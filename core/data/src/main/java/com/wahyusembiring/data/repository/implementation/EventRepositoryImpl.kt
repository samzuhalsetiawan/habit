package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.local.ExamDao
import com.wahyusembiring.data.local.HomeworkDao
import com.wahyusembiring.data.local.ReminderDao
import com.wahyusembiring.data.model.Event
import com.wahyusembiring.data.model.Reminder
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.data.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val examDao: ExamDao,
    private val homewookDao: HomeworkDao,
    private val reminderDao: ReminderDao
) : EventRepository {

    override fun getEvents(): Flow<Map<Event, Subject?>> {
        val exams = examDao.getAllExamWithSubject()
        val homeworks = homewookDao.getAllHomeworkWithSubject()
        val reminders: Flow<Map<Reminder, Subject?>> =
            reminderDao.getAllReminder().map { it.associateWith { null } }
        return combine(exams, homeworks, reminders) { exam, homework, reminder ->
            exam + homework + reminder
        }
    }
}