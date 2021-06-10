package com.aleksejantonov.core.db.impl.store

import com.aleksejantonov.core.db.api.data.DatabaseClientApi
import com.aleksejantonov.core.db.api.store.ProductsStore
import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.db.entity.entity
import com.aleksejantonov.core.db.entity.model
import com.aleksejantonov.core.model.ProductModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ProductsStoreImpl @Inject constructor(
  private val db: DatabaseClientApi,
  private val syncStore: SyncStore,
) : ProductsStore {

  override fun createProduct(product: ProductModel) {
    val productId = db.productDao().insertProduct(product.entity())
    syncStore.putProduct(product = product.copy(id = productId))
  }

  override fun getProduct(id: Long): ProductModel? {
    return db.productDao().getProduct(id)?.model()
  }

  override fun productData(id: Long): Flow<ProductModel> {
    return db.productDao().productData(id).map { entity -> entity.model() }
  }

  override fun productsData(trolleyId: Long): Flow<List<ProductModel>> {
    return db.productDao().productsData(trolleyId).map { it.map { entity -> entity.model() } }
  }

  override fun productsCount(trolleyId: Long): Int {
    return db.productDao().productsCount(trolleyId).toInt()
  }

  override fun getProductIdsByTrolleyId(trolleyId: Long): List<Long> {
    return db.productDao().getProductIdsByTrolleyId(trolleyId)
  }

  override fun setChecked(id: Long, checked: Boolean) {
    db.productDao().setChecked(id, checked)
  }

  override fun toggleChecked(id: Long) {
    db.productDao().toggleChecked(id)
  }

  override fun deleteProduct(productId: Long) {
    db.productDao().deleteProduct(productId = productId)
    syncStore.deleteProduct(productId = productId)
  }

  override fun deleteProductsInTrolley(trolleyId: Long) {
    val pendingProductIdsToRemove = getProductIdsByTrolleyId(trolleyId)
    db.productDao().deleteProductsInTrolley(trolleyId = trolleyId)
    syncStore.deleteProducts(productIds = pendingProductIdsToRemove)
  }
}