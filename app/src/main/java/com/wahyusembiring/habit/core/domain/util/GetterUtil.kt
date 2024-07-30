package com.wahyusembiring.habit.core.domain.util

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.ui.geometry.Size
import androidx.core.database.getLongOrNull
import androidx.navigation.NavBackStackEntry
import com.wahyusembiring.habit.core.domain.model.Attachment
import com.wahyusembiring.habit.core.domain.model.File
import com.wahyusembiring.habit.core.domain.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

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

   fun getCurrentDateTime(): Calendar {
      val date = Date(System.currentTimeMillis())
      val calendar = Calendar.getInstance()
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         val instant = date.toInstant()
         val zonedDateTime = instant.atZone(ZoneId.systemDefault())
         calendar.set(
            zonedDateTime.year,
            zonedDateTime.monthValue,
            zonedDateTime.dayOfMonth,
            zonedDateTime.hour,
            zonedDateTime.minute,
         )
      } else {
         calendar.set(
            date.year,
            date.month,
            date.date,
            date.hours,
            date.minutes
         )
      }
      return calendar
   }

   suspend fun getPhotoMetadata(context: Context, uri: Uri): Photo {
      return withContext(Dispatchers.IO) {

         val contentUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
         } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
         }

         val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.MIME_TYPE,
         )

         val selection = "${MediaStore.Images.Media._ID} = ?"
         val selectionArgs = arrayOf(uri.lastPathSegment)

         context.contentResolver.query(
            contentUri,
            projection,
            selection,
            selectionArgs,
            null
         )?.use { cursor ->
            val displayNameColumnIndex =
               cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val mimeTypeColumnIndex =
               cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

            if (cursor.moveToFirst()) {
               val fileName = cursor.getString(displayNameColumnIndex)
               val size = cursor.getLong(sizeColumnIndex)
               val mimeType = cursor.getString(mimeTypeColumnIndex)

               return@use Photo(uri, fileName, size, mimeType)
            } else {
               throw Error("Unexpected null returned by cursor.moveToFirst() method")
            }
         } ?: throw Error("Unexpected null returned by contentResolver query")
      }
   }

   suspend fun getFileMetadata(context: Context, uri: Uri): File {
      return withContext(Dispatchers.IO) {

         context.contentResolver.query(
            uri,
            null,
            null,
            null,
            null
         )?.use { cursor ->
            val displayNameColumnIndex =
               cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
            val sizeColumnIndex = cursor.getColumnIndexOrThrow(OpenableColumns.SIZE)

            if (cursor.moveToFirst()) {
               val fileName = cursor.getString(displayNameColumnIndex)
               val size = cursor.getLongOrNull(sizeColumnIndex)

               return@use File(uri, fileName, size)
            } else {
               throw Error("Unexpected null returned by cursor.moveToFirst() method")
            }
         } ?: throw Error("Unexpected null returned by contentResolver query")
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
}