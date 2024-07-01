package com.samzuhalsetiawan.habbits.models

import java.time.DayOfWeek

data class Reminder(
   var hour: Int,
   var minute: Int,
   var time: Long,
   var formattedTime: String,
   var days: List<DayOfWeek>
)
