package com.aleksejantonov.core.db.api.dao

import androidx.room.*
import com.aleksejantonov.core.db.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertProduct(product: ProductEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertProducts(products: List<ProductEntity>)

  @Query("DELETE FROM products WHERE id = :productId")
  fun deleteProduct(productId: Long)

  @Query("DELETE FROM products WHERE trolleyId = :trolleyId")
  fun deleteProductsInTrolley(trolleyId: Long)

  @Query("SELECT * FROM products WHERE id = :id")
  fun getProduct(id: Long): ProductEntity?

  @Query("SELECT * FROM products WHERE id in (:ids)")
  fun getProducts(ids: Set<Long>): List<ProductEntity>

  @Query("SELECT * FROM products WHERE id = :id")
  fun productData(id: Long): Flow<ProductEntity>

  @Query("SELECT * FROM products WHERE trolleyId = :trolleyId")
  fun productsData(trolleyId: Long): Flow<List<ProductEntity>>

  @Query("SELECT COUNT(*) FROM products WHERE trolleyId = :trolleyId")
  fun productsCount(trolleyId: Long): Long

  @Query("SELECT id FROM products WHERE trolleyId = :trolleyId")
  fun getProductIdsByTrolleyId(trolleyId: Long): List<Long>

  @Query("UPDATE products SET isChecked = :checked WHERE id = :id")
  fun setChecked(id: Long, checked: Boolean)

  @Query("UPDATE products SET isChecked = NOT isChecked WHERE id = :id")
  fun toggleChecked(id: Long)

  @Query("UPDATE products SET syncStatus = :status WHERE id = :productId")
  fun updateStatus(productId: Long, status: Int)
}