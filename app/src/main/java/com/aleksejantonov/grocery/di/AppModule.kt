package com.aleksejantonov.grocery.di

import android.app.Application
import android.content.Context
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.db.impl.di.DatabaseComponentDependencies
import com.aleksejantonov.core.db.impl.di.DatabaseComponentHolder
import com.aleksejantonov.core.di.DispatcherIO
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class AppModule {

  @Provides
  @Singleton
  fun providesDatabaseApi(
    application: Application,
    @DispatcherIO dispatcher: CoroutineDispatcher,
  ): CoreDatabaseApi {
    return DatabaseComponentHolder.initComponent(
      object : DatabaseComponentDependencies {
        override fun context(): Context = application.baseContext
        override fun dispatcherIO(): CoroutineDispatcher = dispatcher
      }
    ).first
  }

}