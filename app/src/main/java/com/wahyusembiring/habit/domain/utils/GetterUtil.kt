package com.wahyusembiring.habit.domain.utils

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.ui.geometry.Size
import androidx.navigation.NavBackStackEntry

object GetterUtil {

   /**
    * Simple name of the class route without package name, param or query
    * */
   val NavBackStackEntry?.routeSimpleClassName: String?
      get() = this?.destination?.route?.substringBefore("?")?.substringBefore("/")
         ?.substringAfterLast(".")

   fun getWindowSize(context: Context): Size {
      val windowManager =
         context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
      var width = 0
      var height = 0
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
         val bounds = windowManager.currentWindowMetrics.bounds
         width = bounds.width()
         height = bounds.height()
      } else {
         val displayMetrics = DisplayMetrics()
         windowManager.defaultDisplay.getMetrics(displayMetrics)
         width = displayMetrics.widthPixels
         height = displayMetrics.heightPixels
      }
      return Size(width.toFloat(), height.toFloat())
   }
}