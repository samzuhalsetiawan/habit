package com.samzuhalsetiawan.habbits

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

class HabitAlarmManager(
   private val context: Context
) {

   private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

   fun scheduleAlarm(habitName: String, reminderId: Int, hour: Int, minute: Int) {

      val alarmPendingIntent: PendingIntent = PendingIntent.getBroadcast(
         context,
         reminderId,
         Intent(context, HabitAlarmReceiver::class.java).apply {
            putExtra("habitName", habitName)
         },
         PendingIntent.FLAG_IMMUTABLE
      )

      val calendar: Calendar = Calendar.getInstance().apply {
         timeInMillis = System.currentTimeMillis()
         set(Calendar.HOUR_OF_DAY, hour)
         set(Calendar.MINUTE, minute)
      }

      alarmManager.setRepeating(
         AlarmManager.RTC_WAKEUP,
         calendar.timeInMillis,
         1000 * 60 * 60 * 24,
         alarmPendingIntent
      )

   }

   fun cancelAlarm(reminderId: Int) {
      val alarmPendingIntent: PendingIntent = PendingIntent.getBroadcast(
         context,
         reminderId,
         Intent(context, HabitAlarmReceiver::class.java),
         PendingIntent.FLAG_IMMUTABLE
      )
      alarmManager.cancel(alarmPendingIntent)
   }

}