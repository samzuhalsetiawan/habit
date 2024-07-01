package com.samzuhalsetiawan.habbits

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.samzuhalsetiawan.habbits.constants.NOTIFICATION_CHANNEL_ID

class HabitAlarmReceiver : BroadcastReceiver() {
   override fun onReceive(context: Context, intent: Intent) {
      if (intent.action == "android.intent.action.BOOT_COMPLETED") {
         //TODO: Reschedule alarm here.
//         Manifest still not setup corectly
      }

      val habitName = intent.getStringExtra("habitName")

      val builder =
         NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(habitName)
            .setContentText("Waktunya mengerjakan habit ini")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

      if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
         ) == PackageManager.PERMISSION_GRANTED
      ) {
         NotificationManagerCompat.from(context).notify(1, builder.build())
      }

   }
}