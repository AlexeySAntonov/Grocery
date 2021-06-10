package com.aleksejantonov.core.db.impl.di

import android.content.Context
import androidx.room.Room
import com.aleksejantonov.core.db.api.data.DatabaseClientApi
import com.aleksejantonov.core.db.api.store.ProductsStore
import com.aleksejantonov.core.db.api.store.Cleaner
import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.db.api.store.TrolleysStore
import com.aleksejantonov.core.db.impl.BuildConfig
import com.aleksejantonov.core.db.impl.data.GroceryDatabaseImpl
import com.aleksejantonov.core.db.impl.store.ProductsStoreImpl
import com.aleksejantonov.core.db.impl.store.CleanerImpl
import com.aleksejantonov.core.db.impl.store.SyncStoreImpl
import com.aleksejantonov.core.db.impl.store.TrolleysStoreImpl
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DatabaseModule {

  @Singleton
  @Binds
  internal abstract fun provideCleaner(cleaner: CleanerImpl): Cleaner

  @Singleton
  @Binds
  internal abstract fun provideSyncStore(store: SyncStoreImpl): SyncStore

  @Singleton
  @Binds
  internal abstract fun provideTrolleysStore(store: TrolleysStoreImpl): TrolleysStore

  @Singleton
  @Binds
  internal abstract fun provideProductsStore(store: ProductsStoreImpl): ProductsStore

  companion object {

    @Singleton
    @Provides
    internal fun provideDatabaseClientApi(context: Context): DatabaseClientApi {
      return Room.databaseBuilder(context, GroceryDatabaseImpl::class.java, "grocery_db")
        .fallbackToDestructiveMigration()
        .build()
    }

    @Singleton
    @Provides
    internal fun provideRemoteDatabaseClientApi(): DatabaseReference {
      // Use your Firebase database url here
      return FirebaseDatabase
        .getInstance(BuildConfig.FIREBASE_DATABASE_URL)
        .reference
    }

  }

}