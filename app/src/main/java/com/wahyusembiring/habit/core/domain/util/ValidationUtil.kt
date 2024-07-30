package com.wahyusembiring.habit.core.domain.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat

object ValidationUtil {

   fun String?.isNotNullOrBlank(): Boolean = this?.isNotBlank() ?: false

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
}