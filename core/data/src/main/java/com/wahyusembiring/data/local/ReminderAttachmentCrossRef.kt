package com.wahyusembiring.data.local

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Reminder

@Entity(
   tableName = "reminder_attachment",
   primaryKeys = ["reminder_id", "attachment_id"],
   foreignKeys = [
      ForeignKey(
         entity = Reminder::class,
         parentColumns = ["id"],
         childColumns = ["reminder_id"],
         onDelete = ForeignKey.CASCADE,
         onUpdate = ForeignKey.CASCADE,
      ),
      ForeignKey(
         entity = Attachment::class,
         parentColumns = ["uri"],
         childColumns = ["attachment_id"],
         onDelete = ForeignKey.CASCADE,
         onUpdate = ForeignKey.CASCADE
      )
   ]
)
data class ReminderAttachmentCrossRef(
   @ColumnInfo(name = "reminder_id")
   val reminderId: Int,

   @ColumnInfo(name = "attachment_id")
   val attachmentId: Uri
)