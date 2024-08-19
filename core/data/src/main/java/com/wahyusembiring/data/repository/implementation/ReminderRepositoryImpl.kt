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

   override suspend fun getRemindersAsFlow(): Flow<List<Reminder>> {
      return reminderDao.getAllReminderAsFlow()
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
}