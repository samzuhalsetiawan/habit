package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.local.ExamDao
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Exam
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.data.repository.ExamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
    private val examDao: ExamDao
) : ExamRepository {

    override fun getAllExam(minDate: Long?, maxDate: Long?): Flow<Map<Exam, Subject>> {
        return if (minDate == null || maxDate == null) {
            examDao.getAllExamWithSubject()
        } else {
            examDao.getAllExamWithSubject(minDate, maxDate)
        }
    }

    override suspend fun saveExam(exam: Exam, attachments: List<Attachment>) {
        if (attachments.isEmpty()) {
            examDao.insertExam(exam)
        } else {
            examDao.insertExamWithAttachments(exam, attachments)
        }
    }

    override suspend fun updateExam(exam: Exam) {
        examDao.updateExam(exam)
    }
}