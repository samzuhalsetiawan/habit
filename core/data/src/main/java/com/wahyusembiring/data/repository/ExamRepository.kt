package com.wahyusembiring.data.repository

import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Exam
import kotlinx.coroutines.flow.Flow

interface ExamRepository {
   suspend fun getAllExamsAsFlow(): Flow<List<Exam>>
   suspend fun saveExam(exam: Exam, attachments: List<Attachment>)
}