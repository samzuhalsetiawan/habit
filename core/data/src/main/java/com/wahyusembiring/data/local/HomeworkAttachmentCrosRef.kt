package com.wahyusembiring.data.local

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Homework


@Entity(
   tableName = "homework_attachment",
   primaryKeys = ["homeworkId", "attachmentId"],
   foreignKeys = [
      ForeignKey(
         entity = Homework::class,
         parentColumns = ["id"],
         childColumns = ["homeworkId"],
         onDelete = ForeignKey.CASCADE,
         onUpdate = ForeignKey.CASCADE
      ),
      ForeignKey(
         entity = Attachment::class,
         parentColumns = ["uri"],
         childColumns = ["attachmentId"],
         onDelete = ForeignKey.CASCADE,
         onUpdate = ForeignKey.CASCADE
      )
   ]
)
data class HomeworkAttachmentCrosRef(
   val homeworkId: Int,
   val attachmentId: Uri
)
