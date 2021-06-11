package com.aleksejantonov.core.network.util.di

import android.app.Application
import com.aleksejantonov.core.network.util.NetworkStateListener
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

  @Provides
  @Singleton
  fun providesNetworkStateListener(application: Application) = NetworkStateListener(application)

}