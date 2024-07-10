package com.wahyusembiring.habit

import android.app.Application
import com.wahyusembiring.habit.di.AppModule
import com.wahyusembiring.habit.di.AppModuleImpl

class App : Application() {
   companion object {
      lateinit var appModule: AppModule
   }

   override fun onCreate() {
      super.onCreate()
      appModule = AppModuleImpl(this)
   }
}