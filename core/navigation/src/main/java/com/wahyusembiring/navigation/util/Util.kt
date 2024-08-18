package com.wahyusembiring.navigation.util

import androidx.navigation.NavBackStackEntry

/**
 * Simple name of the class route without package name, param or query
 * */
val NavBackStackEntry?.routeSimpleClassName: String?
   get() = this?.destination?.route?.substringBefore("?")?.substringBefore("/")
      ?.substringAfterLast(".")