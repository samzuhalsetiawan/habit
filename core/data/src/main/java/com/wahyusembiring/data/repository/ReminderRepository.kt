package com.wahyusembiring.data.repository

import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

   suspend fun getRemindersAsFlow(): Flow<List<Reminder>>

   suspend fun saveReminderWithAttachments(
      reminder: Reminder,
      attachments: List<Attachment>
   )

}