package com.samzuhalsetiawan.habbits

import android.app.Application
import android.content.Context
import androidx.datastore.dataStore
import com.samzuhalsetiawan.habbits.di.RepositoryModule
import com.samzuhalsetiawan.habbits.di.RepositoryModuleImpl
import com.samzuhalsetiawan.habbits.singletons.AppSettingsSerializer

class App : Application() {

   companion object {
      lateinit var repositoryModule: RepositoryModule
         private set
   }

   override fun onCreate() {
      super.onCreate()
      repositoryModule = RepositoryModuleImpl(this)
   }
}

val Context.appSettingsDS by dataStore("app-settings.json", AppSettingsSerializer)