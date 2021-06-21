package com.aleksejantonov.core.db.impl.di

import android.content.Context
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.DispatcherIO
import com.aleksejantonov.module.injector.BaseDependencies
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Component(modules = [DatabaseModule::class])
@Singleton
interface DatabaseComponent : CoreDatabaseApi {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun context(context: Context): Builder

    @BindsInstance
    fun dispatcherIO(@DispatcherIO dispatcher: CoroutineDispatcher): Builder

    fun build(): DatabaseComponent
  }

  companion object {
    const val DATABASE_COMPONENT_KEY = "DATABASE_COMPONENT_KEY"

    fun init(dependencies: DatabaseComponentDependencies): CoreDatabaseApi {
      return DaggerDatabaseComponent.builder()
        .context(dependencies.context())
        .dispatcherIO(dependencies.dispatcherIO())
        .build()
    }
  }
}

interface DatabaseComponentDependencies : BaseDependencies {
  fun context(): Context
  fun dispatcherIO(): CoroutineDispatcher
}