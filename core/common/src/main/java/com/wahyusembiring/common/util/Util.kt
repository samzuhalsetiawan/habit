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

fun String?.isNotNullOrBlank(): Boolean = this?.isNotBlank() ?: false

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(block = block)
}

fun Int.withZeroPadding(length: Int = 2): String = this.toString().padStart(length, '0')
