package com.samzuhalsetiawan.habbits.di

import android.content.Context
import com.samzuhalsetiawan.habbits.repository.MainRepository

class RepositoryModuleImpl(
   private val appContext: Context
) : RepositoryModule {

   override val mainRepository: MainRepository by lazy {
      MainRepository.getInstance(appContext)
   }

}