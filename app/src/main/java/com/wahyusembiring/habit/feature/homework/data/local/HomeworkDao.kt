package com.wahyusembiring.habit.feature.homework.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.wahyusembiring.habit.core.domain.model.Attachment
import com.wahyusembiring.habit.feature.homework.domain.model.Homework
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeworkDao {

   @Insert(
      entity = Homework::class,
      onConflict = OnConflictStrategy.ABORT
   )
   suspend fun insertHomework(homework: Homework): Long

   @Insert(
      entity = HomeworkAttachmentCrosRef::class,
      onConflict = OnConflictStrategy.ABORT
   )
   suspend fun insertHomeworkAttachment(crosRef: HomeworkAttachmentCrosRef)

   @Upsert(entity = Attachment::class)
   suspend fun upsertAttachment(attachment: Attachment): Long

   @Upsert(entity = Attachment::class)
   suspend fun upsertAttachment(attachments: List<Attachment>): List<Long>

   @Transaction
   suspend fun insertHomeworkWithAttachments(
      homework: Homework,
      attachments: List<Attachment>
   ) {
      val homeworkId = insertHomework(homework)
      for (attachment in attachments) {
         upsertAttachment(attachment)
         insertHomeworkAttachment(HomeworkAttachmentCrosRef(homeworkId.toInt(), attachment.uri))
      }
   }

   @Query(
      "SELECT * FROM homework"
   )
   fun getAllHomeworkAsFlow(): Flow<List<Homework>>

}