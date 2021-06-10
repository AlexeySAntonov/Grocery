package com.aleksejantonov.grocery.di

import android.app.Application
import android.content.Context
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.db.impl.di.DatabaseComponentDependencies
import com.aleksejantonov.core.db.impl.di.DatabaseComponentHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

  @Provides
  @Singleton
  fun providesDatabaseApi(application: Application): CoreDatabaseApi {
    return DatabaseComponentHolder.initComponent(
      object : DatabaseComponentDependencies {
        override fun context(): Context = application.baseContext
      }
    ).first
  }

}