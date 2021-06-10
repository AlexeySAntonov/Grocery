package com.aleksejantonov.grocery

import android.app.Application
import com.aleksejantonov.grocery.di.AppComponentDependencies
import com.aleksejantonov.grocery.di.AppComponentHolder
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    AppComponentHolder.initComponent(object : AppComponentDependencies {
      override fun application(): Application = this@App
    })
    AndroidThreeTen.init(this)
    initTimber()
  }

  private fun initTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}