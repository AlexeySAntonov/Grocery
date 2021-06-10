package com.aleksejantonov.core.db.api.store

import com.aleksejantonov.core.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductsStore {
  fun createProduct(product: ProductModel)
  fun getProduct(id: Long): ProductModel?
  fun productData(id: Long): Flow<ProductModel>
  fun productsData(trolleyId: Long): Flow<List<ProductModel>>
  fun productsCount(trolleyId: Long): Int
  fun getProductIdsByTrolleyId(trolleyId: Long): List<Long>
  fun setChecked(id: Long, checked: Boolean)
  fun toggleChecked(id: Long)
  fun deleteProduct(productId: Long)
  fun deleteProductsInTrolley(trolleyId: Long)
}