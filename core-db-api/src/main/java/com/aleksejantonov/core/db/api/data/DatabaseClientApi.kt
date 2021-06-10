package com.aleksejantonov.core.db.api.data

import com.aleksejantonov.core.db.api.dao.ProductDao
import com.aleksejantonov.core.db.api.dao.TrolleyDao

interface DatabaseClientApi {
  fun inTransaction(block: () -> Unit)
  fun clearAll()

  fun productDao(): ProductDao
  fun trolleyDao(): TrolleyDao
}