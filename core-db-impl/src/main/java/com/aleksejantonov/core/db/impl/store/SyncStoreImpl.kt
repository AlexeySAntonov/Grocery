package com.aleksejantonov.core.db.impl.store

import android.util.Log
import com.aleksejantonov.core.db.api.data.DatabaseClientApi
import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.db.entity.dto
import com.aleksejantonov.core.db.entity.entity
import com.aleksejantonov.core.db.impl.data.PRODUCT_PREFIX
import com.aleksejantonov.core.db.impl.data.TABLE_PRODUCTS
import com.aleksejantonov.core.db.impl.data.TABLE_TROLLEYS
import com.aleksejantonov.core.db.impl.data.TROLLEY_PREFIX
import com.aleksejantonov.core.model.ProductModel
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.model.TrolleyModel
import com.aleksejantonov.core.model.dto.ProductDto
import com.aleksejantonov.core.model.dto.TrolleyDto
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SyncStoreImpl @Inject constructor(
  private val remoteDatabase: DatabaseReference,
  private val localDatabase: DatabaseClientApi,
) : SyncStore {

  private val asyncExecutor by lazy {
    Executor { command ->
      GlobalScope.launch(Dispatchers.IO) {
        command?.run()
      }
    }
  }

  override fun fetchRemoteData(): Flow<SyncStatus> = callbackFlow {
    remoteDatabase.child(TABLE_TROLLEYS).get()
      .addOnSuccessListener(asyncExecutor) trCallback@{ trolleysSnapshot ->
        val trolleys = trolleysSnapshot.getValue(object : GenericTypeIndicator<HashMap<String, TrolleyDto>>() {})?.values ?: run {
          offer(SyncStatus.DONE)
          channel.close()
          return@trCallback
        }
        localDatabase.trolleyDao().insertTrolleys(trolleys = trolleys.map { dto -> dto.entity() })

        val existentRemoteTrolleyIds = trolleys.map { it.id }.toSet()
        remoteDatabase.child(TABLE_PRODUCTS).get()
          .addOnSuccessListener(asyncExecutor) prCallback@{ productsSnapshot ->
            val products = productsSnapshot.getValue(object : GenericTypeIndicator<HashMap<String, ProductDto>>() {})?.values ?: run {
              offer(SyncStatus.DONE)
              channel.close()
              return@prCallback
            }
            val filteredProducts = products.filter { existentRemoteTrolleyIds.contains(it.trolleyId) }
            localDatabase.productDao().insertProducts(filteredProducts.map { it.entity() })

            offer(SyncStatus.DONE)
            channel.close()
          }
          .addOnCanceledListener(asyncExecutor) {
            offer(SyncStatus.CANCELED)
            channel.close()
          }
          .addOnFailureListener(asyncExecutor) {
            Log.e("SYNC EX", it.toString())
            offer(SyncStatus.FAILED)
            channel.close()
          }
      }
      .addOnCanceledListener(asyncExecutor) {
        offer(SyncStatus.CANCELED)
        channel.close()
      }
      .addOnFailureListener(asyncExecutor) {
        Log.e("SYNC EX", it.toString())
        offer(SyncStatus.FAILED)
        channel.close()
      }
    awaitClose()
  }

  override fun putTrolley(trolley: TrolleyModel) {
    localDatabase.trolleyDao().updateStatus(trolleyId = trolley.id, status = SyncStatus.UPDATING.ordinal)
    // Trick to update SyncStatus.FAILED, because somehow PUT operations don't receive this callback
    remoteDatabase.child(TABLE_TROLLEYS).get()
      .addOnFailureListener(asyncExecutor) { localDatabase.trolleyDao().updateStatus(trolleyId = trolley.id, status = SyncStatus.FAILED.ordinal) }

    remoteDatabase.child(TABLE_TROLLEYS).child("$TROLLEY_PREFIX${trolley.id}").setValue(trolley.dto())
      .addOnSuccessListener(asyncExecutor) {
        localDatabase.trolleyDao().updateStatus(trolleyId = trolley.id, status = SyncStatus.DONE.ordinal)
        trolley.products.forEach { putProduct(it) }
      }
      .addOnCanceledListener(asyncExecutor) { localDatabase.trolleyDao().updateStatus(trolleyId = trolley.id, status = SyncStatus.CANCELED.ordinal) }
  }

  override fun deleteTrolley(trolleyId: Long, productIds: List<Long>) {
    remoteDatabase.child(TABLE_TROLLEYS).child("$TROLLEY_PREFIX$trolleyId").removeValue()
    deleteProducts(productIds = productIds)
  }

  override fun deleteAllTrolleys() {
    remoteDatabase.child(TABLE_TROLLEYS).removeValue()
    remoteDatabase.child(TABLE_PRODUCTS).removeValue()
  }

  override fun putProduct(product: ProductModel) {
    localDatabase.productDao().updateStatus(productId = product.id, status = SyncStatus.UPDATING.ordinal)
    // Trick to update SyncStatus.FAILED, because somehow PUT operations don't receive this callback
    remoteDatabase.child(TABLE_TROLLEYS).get()
      .addOnFailureListener(asyncExecutor) { localDatabase.productDao().updateStatus(productId = product.id, status = SyncStatus.FAILED.ordinal) }

    remoteDatabase.child(TABLE_PRODUCTS).child("$PRODUCT_PREFIX${product.id}").setValue(product.dto())
      .addOnSuccessListener(asyncExecutor) { localDatabase.productDao().updateStatus(productId = product.id, status = SyncStatus.DONE.ordinal) }
      .addOnCanceledListener(asyncExecutor) { localDatabase.productDao().updateStatus(productId = product.id, status = SyncStatus.CANCELED.ordinal) }
  }

  override fun deleteProduct(productId: Long) {
    remoteDatabase.child(TABLE_PRODUCTS).child("$PRODUCT_PREFIX$productId").removeValue()
  }

  override fun deleteProducts(productIds: List<Long>) {
    productIds.forEach { deleteProduct(it) }
  }

}