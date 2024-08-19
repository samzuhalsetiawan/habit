package com.wahyusembiring.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Exam
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {

   @Query("SELECT * FROM exam")
   fun getAllExams(): Flow<List<Exam>>

   @Insert(
      entity = Exam::class,
      onConflict = OnConflictStrategy.ABORT
   )
   suspend fun insertExam(exam: Exam): Long

   @Upsert(entity = Attachment::class)
   suspend fun upsertAttachment(attachment: Attachment): Long

   @Upsert(entity = Attachment::class)
   suspend fun upsertAttachment(attachments: List<Attachment>): List<Long>

   @Insert(
      entity = ExamAttachmentCrossRef::class,
      onConflict = OnConflictStrategy.ABORT
   )
   suspend fun insertExamAttachment(crosRef: ExamAttachmentCrossRef)

   @Transaction
   suspend fun insertExamWithAttachments(
      exam: Exam,
      attachments: List<Attachment>
   ) {
      val examId = insertExam(exam)
      for (attachment in attachments) {
         upsertAttachment(attachment)
         insertExamAttachment(ExamAttachmentCrossRef(examId.toInt(), attachment.uri))
      }
   }

}