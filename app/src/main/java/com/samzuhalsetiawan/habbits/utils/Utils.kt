package com.samzuhalsetiawan.habbits.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavBackStackEntry
import com.samzuhalsetiawan.habbits.data.local.entities.HabitEntity
import com.samzuhalsetiawan.habbits.data.local.entities.HabitHistoryEntity
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.models.HabitHistory
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.reflect.KClass


fun getCurrentLocalDateString(): String {
   return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      LocalDate.now().format(getDateFormatter())
   } else {
      val time = Calendar.getInstance().time
      getDateFormatterLegacy().format(time)
   }
}

fun Long.toLocalDateStringFormatted(): String {
   return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val instant = Instant.ofEpochMilli(this)
      val zoneId = ZoneId.systemDefault() // Use the system default time zone
      val localDate = instant.atZone(zoneId).toLocalDate()
      localDate.format(getDateFormatter())
   } else {
      val date = Date(this)
      getDateFormatterLegacy().format(date)
   }
}

fun Date.formatToTimeString(): String {
   return SimpleDateFormat("HH:mm", Locale("ind", "ID")).format(this)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateFormatter(): DateTimeFormatter {
   return DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ind", "ID"))
}

fun getDateFormatterLegacy(): SimpleDateFormat {
   return SimpleDateFormat("dd MMMM yyyy", Locale("ind", "ID"))
}

fun String?.isNotNullOrBlank(): Boolean = this?.isNotBlank() ?: false

fun HabitEntity.toHabit(): Habit {
   return Habit(
      id = id,
      name = name,
      isGoodHabit = true,
      dayStart = startFrom,
      dayEnd = endUntil,
      isEndless = isEndless,
      repeatEveryday = repeatEveryday,
      types = Habit.Types.entries.first { it.name == habitType }
   )
}

fun HabitHistoryEntity.toHabitHistory(): HabitHistory {
   return HabitHistory(
      status = HabitHistory.Status.entries.first { it.name == status },
      timestamp = dateCreated
   )
}

fun Habit.toHabitEntity(): HabitEntity {
   return HabitEntity(
      name = name,
      habitType = types.name,
      startFrom = dayStart,
      endUntil = dayEnd ?: 0L,
      isEndless = isEndless,
      repeatEveryday = repeatEveryday
   )
}

@Composable
inline fun <reified VM : ViewModel> provideViewModel(
   viewModelStoreOwner: ViewModelStoreOwner? = null,
   noinline initializer: (CreationExtras.() -> VM)? = null
): VM {
   val owner = viewModelStoreOwner ?: checkNotNull(LocalViewModelStoreOwner.current)
   val factory = when (initializer) {
      null -> null
      else -> viewModelFactory {
         addInitializer(
            clazz = VM::class,
            initializer = initializer
         )
         build()
      }
   }
   return viewModel<VM>(viewModelStoreOwner = owner, factory = factory)
}

infix fun String?.isRouteOf(route: KClass<*>): Boolean {
   return route.simpleName == this
}

val NavBackStackEntry?.currentRoute: String?
   get() = this?.destination?.route?.substringBefore("?")?.substringBefore("/")
      ?.substringAfterLast(".")


@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
   var isFocused by remember { mutableStateOf(false) }
   var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
   if (isFocused) {
      val imeIsVisible = WindowInsets.isImeVisible
      val focusManager = LocalFocusManager.current
      LaunchedEffect(imeIsVisible) {
         if (imeIsVisible) {
            keyboardAppearedSinceLastFocused = true
         } else if (keyboardAppearedSinceLastFocused) {
            focusManager.clearFocus()
         }
      }
   }
   onFocusEvent {
      if (isFocused != it.isFocused) {
         isFocused = it.isFocused
         if (isFocused) {
            keyboardAppearedSinceLastFocused = false
         }
      }
   }
}