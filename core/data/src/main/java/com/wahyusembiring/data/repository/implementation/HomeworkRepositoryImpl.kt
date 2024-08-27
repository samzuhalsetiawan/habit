package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Homework
import com.wahyusembiring.data.local.HomeworkDao
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.data.repository.HomeworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeworkRepositoryImpl @Inject constructor(
    private val homeworkDao: HomeworkDao
) : HomeworkRepository {

    override fun getAllHomework(minDate: Long?, maxDate: Long?): Flow<Map<Homework, Subject>> {
        return if (minDate == null || maxDate == null) {
            homeworkDao.getAllHomeworkWithSubject()
        } else {
            homeworkDao.getAllHomeworkWithSubject(minDate, maxDate)
        }
    }

    override suspend fun saveHomework(
        homework: Homework,
        attachments: List<Attachment>
    ) {
        if (attachments.isEmpty()) {
            homeworkDao.insertHomework(homework)
        } else {
            homeworkDao.insertHomeworkWithAttachments(homework, attachments)
        }
    }

    override suspend fun updateHomework(homework: Homework) {
        homeworkDao.updateHomework(homework)
    }

}