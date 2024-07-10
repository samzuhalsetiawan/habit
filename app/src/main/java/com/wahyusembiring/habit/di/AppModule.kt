package com.wahyusembiring.habit.di

import android.content.Context

interface AppModule

class AppModuleImpl(
   private val appContext: Context
) : AppModule