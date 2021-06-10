package com.aleksejantonov.core.db.impl.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aleksejantonov.core.db.api.data.DatabaseClientApi
import com.aleksejantonov.core.db.entity.ProductEntity
import com.aleksejantonov.core.db.entity.TrolleyEntity

@Database(
  entities = [
    TrolleyEntity::class,
    ProductEntity::class
  ],
  version = 1,
  exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GroceryDatabaseImpl : RoomDatabase(), DatabaseClientApi {

  override fun inTransaction(block: () -> Unit) {
    runInTransaction { block.invoke() }
  }

  override fun clearAll() {
    clearAllTables()
  }
}