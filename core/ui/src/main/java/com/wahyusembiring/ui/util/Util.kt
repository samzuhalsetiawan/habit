package com.wahyusembiring.ui.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.ui.geometry.Size
import androidx.core.content.ContextCompat

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

fun checkForPermissionOrLaunchPermissionLauncher(
   context: Context,
   permissionToRequest: List<String>,
   permissionRequestLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
   onPermissionAlreadyGranted: () -> Unit
) {
   if (permissionToRequest.all {
         ContextCompat.checkSelfPermission(
            context,
            it
         ) == PackageManager.PERMISSION_GRANTED
      }) {
      onPermissionAlreadyGranted()
   } else {
      permissionRequestLauncher.launch(permissionToRequest.toTypedArray())
   }
}

fun getPhotoAccessPermissionRequest(): List<String> {
   return when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> listOf(
         Manifest.permission.READ_MEDIA_IMAGES,
         Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
      )

      Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> listOf(Manifest.permission.READ_MEDIA_IMAGES)
      else -> listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
   }
}

fun getFileAccessPermissionRequest(): List<String> {
   return when {
      Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q -> listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
      else -> emptyList()
   }
}