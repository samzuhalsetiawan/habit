package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.local.ExamDao
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Exam
import com.wahyusembiring.data.repository.ExamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
   private val examDao: ExamDao
) : ExamRepository {

   override suspend fun getAllExamsAsFlow(): Flow<List<Exam>> {
      return examDao.getAllExams()
   }

   override suspend fun saveExam(exam: Exam, attachments: List<Attachment>) {
      if (attachments.isEmpty()) {
         examDao.insertExam(exam)
      } else {
         examDao.insertExamWithAttachments(exam, attachments)
      }
   }
}