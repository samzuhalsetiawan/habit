package com.wahyusembiring.data.repository

import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Exam
import com.wahyusembiring.data.model.Subject
import kotlinx.coroutines.flow.Flow

interface ExamRepository {
    fun getAllExam(
        minDate: Long? = null,
        maxDate: Long? = null
    ): Flow<Map<Exam, Subject>>

    suspend fun saveExam(exam: Exam, attachments: List<Attachment>)
    suspend fun updateExam(exam: Exam)
}