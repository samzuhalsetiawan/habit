package com.wahyusembiring.data.util

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.core.database.getLongOrNull
import com.wahyusembiring.data.model.File
import com.wahyusembiring.data.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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