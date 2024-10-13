package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.local.dao.ExamDao
import com.wahyusembiring.data.local.dao.HomeworkDao
import com.wahyusembiring.data.local.dao.ReminderDao
import com.wahyusembiring.data.model.ExamWithSubject
import com.wahyusembiring.data.model.HomeworkWithSubject
import com.wahyusembiring.data.model.entity.Exam
import com.wahyusembiring.data.model.entity.Homework
import com.wahyusembiring.data.model.entity.Reminder
import com.wahyusembiring.data.remote.ExamService
import com.wahyusembiring.data.remote.HomeworkService
import com.wahyusembiring.data.remote.ReminderService
import com.wahyusembiring.data.remote.SubjectService
import com.wahyusembiring.data.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val examDao: ExamDao,
    private val homeworkDao: HomeworkDao,
    private val reminderDao: ReminderDao,
    private val homeworkService: HomeworkService,
    private val examService: ExamService,
    private val reminderService: ReminderService,
    private val subjectService: SubjectService
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
        val homeworkId = homeworkDao.insertHomework(homework)
        homeworkService.saveHomework(homework.copy(id = homeworkId.toInt()))
        return homeworkId
    }

    override suspend fun updateHomework(homework: Homework) {
        homeworkDao.updateHomework(homework)
        homeworkService.saveHomework(homework)
    }

    override fun getAllExamWithSubject(): Flow<List<ExamWithSubject>> {
        return examDao.getAllExamWithSubject()
    }

    override suspend fun saveExam(exam: Exam): Long {
        val examId = examDao.insertExam(exam)
        examService.saveExam(exam.copy(id = examId.toInt()))
        return examId
    }

    override suspend fun updateExam(exam: Exam) {
        examDao.updateExam(exam)
        examService.saveExam(exam)
    }

    override fun getAllReminder(minDate: Long?, maxDate: Long?): Flow<List<Reminder>> {
        return if (minDate == null || maxDate == null) {
            reminderDao.getAllReminder()
        } else {
            reminderDao.getAllReminder(minDate, maxDate)
        }
    }

    override suspend fun saveReminder(reminder: Reminder): Long {
        val reminderId = reminderDao.insertReminder(reminder)
        reminderService.saveReminder(reminder.copy(id = reminderId.toInt()))
        return reminderId
    }

    override suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateReminder(reminder)
        reminderService.saveReminder(reminder)
    }

    override suspend fun deleteExam(exam: Exam) {
        examDao.deleteExam(exam)
        examService.deleteExam(exam)
    }

    override suspend fun deleteHomework(homework: Homework) {
        homeworkDao.deleteHomework(homework)
        homeworkService.deleteHomework(homework)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
        reminderService.deleteReminder(reminder)
    }

    override fun getExamById(id: Int): Flow<ExamWithSubject?> {
        return examDao.getExamById(id)
    }

    override fun getReminderById(id: Int): Flow<Reminder?> {
        return reminderDao.getReminderById(id)
    }
}