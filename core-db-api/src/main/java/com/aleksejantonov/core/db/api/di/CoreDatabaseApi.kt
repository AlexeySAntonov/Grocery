package com.aleksejantonov.core.db.api.di

import com.aleksejantonov.core.db.api.store.ProductsStore
import com.aleksejantonov.core.db.api.store.Cleaner
import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.db.api.store.TrolleysStore
import com.aleksejantonov.module.injector.BaseApi

interface CoreDatabaseApi : BaseApi {
  fun cleaner(): Cleaner
  fun syncStore(): SyncStore
  fun trolleysStore(): TrolleysStore
  fun productsStore(): ProductsStore
}