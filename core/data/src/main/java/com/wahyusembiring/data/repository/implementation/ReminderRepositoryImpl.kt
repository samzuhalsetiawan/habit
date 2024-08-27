package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.local.ReminderDao
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Reminder
import com.wahyusembiring.data.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao
) : ReminderRepository {

    override fun getAllReminder(minDate: Long?, maxDate: Long?): Flow<List<Reminder>> {
        return if (minDate == null || maxDate == null) {
            reminderDao.getAllReminder()
        } else {
            reminderDao.getAllReminder(minDate, maxDate)
        }
    }

    override suspend fun saveReminderWithAttachments(
        reminder: Reminder,
        attachments: List<Attachment>
    ) {
        if (attachments.isEmpty()) {
            reminderDao.insertReminder(reminder)
        } else {
            reminderDao.insertReminderWithAttachments(reminder, attachments)
        }
    }

    override suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateReminder(reminder)
    }
}