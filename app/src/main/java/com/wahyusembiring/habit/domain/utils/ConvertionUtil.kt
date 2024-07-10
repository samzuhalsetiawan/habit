package com.wahyusembiring.habit.domain.utils

import androidx.navigation.NavBackStackEntry

object ConvertionUtil {

   /**
    * Simple name of the class route without package name, param or query
    * */
   val NavBackStackEntry?.routeName: String?
      get() = this?.destination?.route?.substringBefore("?")?.substringBefore("/")
         ?.substringAfterLast(".")

}