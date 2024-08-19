package com.wahyusembiring.data.local

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Exam

@Entity(
   tableName = "exam_attachment",
   primaryKeys = ["exam_id", "attachment_id"],
   foreignKeys = [
      ForeignKey(
         entity = Exam::class,
         parentColumns = ["id"],
         childColumns = ["exam_id"],
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
data class ExamAttachmentCrossRef(

   @ColumnInfo(name = "exam_id")
   val examId: Int,

   @ColumnInfo(name = "attachment_id")
   val attachmentId: Uri

)