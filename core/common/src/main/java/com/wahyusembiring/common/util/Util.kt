package com.wahyusembiring.common.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.completeWith
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.reflect.KMutableProperty0

fun Date.formatToString(pattern: String): String {
   return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val instant = this.toInstant()
      val formatter = DateTimeFormatter.ofPattern(pattern)
      instant.atZone(ZoneId.systemDefault()).format(formatter)
   } else {
      val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
      return formatter.format(this)
   }
}

/**
 * Create a single instance of an object and store it in [volatileInstanceProp] property.
 *
 * @param volatileInstanceProp property to store the created instance
 * @param initializer a lambda function to create the instance
 *
 * @return [T] the created instance or the existing instance if it already exists
 * */
fun <T> createSingleton(
   volatileInstanceProp: KMutableProperty0<T?>,
   initializer: () -> T
): T {
   return volatileInstanceProp.get() ?: synchronized(Any()) {
      volatileInstanceProp.get() ?: initializer().also {
         volatileInstanceProp.set(it)
      }
   }
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

fun String?.isNotNullOrBlank(): Boolean = this?.isNotBlank() ?: false

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit): Job {
   return viewModelScope.launch(block = block)
}
