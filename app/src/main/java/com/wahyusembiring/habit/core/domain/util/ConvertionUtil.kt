package com.wahyusembiring.habit.core.domain.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

object ConvertionUtil {

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

}