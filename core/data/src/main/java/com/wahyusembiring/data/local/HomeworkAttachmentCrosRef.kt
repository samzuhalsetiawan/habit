package com.wahyusembiring.data.local

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Homework


@Entity(
   tableName = "homework_attachment",
   primaryKeys = ["homework_id", "attachment_id"],
   foreignKeys = [
      ForeignKey(
         entity = Homework::class,
         parentColumns = ["id"],
         childColumns = ["homework_id"],
         onDelete = ForeignKey.CASCADE,
         onUpdate = ForeignKey.CASCADE
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
data class HomeworkAttachmentCrosRef(

   @ColumnInfo(name = "homework_id")
   val homeworkId: Int,

   @ColumnInfo(name = "attachment_id")
   val attachmentId: Uri

)
