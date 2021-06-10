package com.aleksejantonov.grocery.di


import android.app.Application
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.navigation.di.NavigationModule
import com.aleksejantonov.module.injector.BaseApi
import com.aleksejantonov.module.injector.BaseDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AppModule::class,
    FeatureProviderModule::class,
    NavigationModule::class,
  ]
)
interface AppComponent : AppComponentApi {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(app: Application): Builder

    fun build(): AppComponent
  }

  companion object {
    const val APP_COMPONENT_KEY = "APP_COMPONENT_KEY"

    fun init(dependencies: AppComponentDependencies): AppComponent {
      return DaggerAppComponent.builder()
        .application(dependencies.application())
        .build()
    }
  }
}

interface AppComponentApi : BaseApi {
  fun featureProvider(): GlobalFeatureProvider
  fun router(): GlobalRouter
}

interface AppComponentDependencies : BaseDependencies {
  fun application(): Application
}