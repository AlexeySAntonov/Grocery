package com.aleksejantonov.grocery.di

import com.aleksejantonov.core.di.DispatcherDefault
import com.aleksejantonov.core.di.DispatcherIO
import com.aleksejantonov.core.di.DispatcherMain
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DispatchersModule {

  @Provides
  @Singleton
  @DispatcherMain
  fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

  @Provides
  @Singleton
  @DispatcherIO
  fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @Singleton
  @DispatcherDefault
  fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}