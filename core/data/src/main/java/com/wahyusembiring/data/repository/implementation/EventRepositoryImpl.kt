package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.local.dao.ExamDao
import com.wahyusembiring.data.local.dao.HomeworkDao
import com.wahyusembiring.data.local.dao.ReminderDao
import com.wahyusembiring.data.model.ExamWithSubject
import com.wahyusembiring.data.model.HomeworkWithSubject
import com.wahyusembiring.data.model.entity.Exam
import com.wahyusembiring.data.model.entity.Homework
import com.wahyusembiring.data.model.entity.Reminder
import com.wahyusembiring.data.repository.EventRepository
import com.wahyusembiring.data.repository.ExamRepository
import com.wahyusembiring.data.repository.HomeworkRepository
import com.wahyusembiring.data.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class EventRepositoryImpl(
    private val examDao: ExamDao,
    private val homeworkDao: HomeworkDao,
    private val reminderDao: ReminderDao
) : EventRepository {

    override fun getAllEvent(): Flow<List<Any>> {
        val homeworkFlow = homeworkDao.getAllHomeworkWithSubject()
        val examFlow = examDao.getAllExamWithSubject()
        val reminderFlow = reminderDao.getAllReminder()
        return combine(homeworkFlow, examFlow, reminderFlow) { homework, exam, reminder ->
            val events = mutableListOf<Any>()
            events.addAll(homework)
            events.addAll(exam)
            events.addAll(reminder)
            events
        }
    }

    override fun getAllHomeworkWithSubject(
        minDate: Long?,
        maxDate: Long?
    ): Flow<List<HomeworkWithSubject>> {
        return if (minDate == null || maxDate == null) {
            homeworkDao.getAllHomeworkWithSubject()
        } else {
            homeworkDao.getAllHomeworkWithSubject(minDate, maxDate)
        }
    }

    override fun getHomeworkById(id: Int): Flow<HomeworkWithSubject?> {
        return homeworkDao.getHomeworkById(id)
    }

    override suspend fun saveHomework(homework: Homework): Long {
        return homeworkDao.insertHomework(homework)
    }

    override suspend fun updateHomework(homework: Homework) {
        return homeworkDao.updateHomework(homework)
    }

    override fun getAllExamWithSubject(): Flow<List<ExamWithSubject>> {
        return examDao.getAllExamWithSubject()
    }

    override suspend fun saveExam(exam: Exam): Long {
        return examDao.insertExam(exam)
    }

    override suspend fun updateExam(exam: Exam) {
        return examDao.updateExam(exam)
    }

    override fun getAllReminder(minDate: Long?, maxDate: Long?): Flow<List<Reminder>> {
        return if (minDate == null || maxDate == null) {
            reminderDao.getAllReminder()
        } else {
            reminderDao.getAllReminder(minDate, maxDate)
        }
    }

    override suspend fun saveReminder(reminder: Reminder): Long {
        return reminderDao.insertReminder(reminder)
    }

    override suspend fun updateReminder(reminder: Reminder) {
        return reminderDao.updateReminder(reminder)
    }

    override suspend fun deleteExam(exam: Exam) {
        return examDao.deleteExam(exam)
    }

    override suspend fun deleteHomework(homework: Homework) {
        return homeworkDao.deleteHomework(homework)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        return reminderDao.deleteReminder(reminder)
    }

    override fun getExamById(id: Int): Flow<ExamWithSubject?> {
        return examDao.getExamById(id)
    }

    override fun getReminderById(id: Int): Flow<Reminder?> {
        return reminderDao.getReminderById(id)
    }
}