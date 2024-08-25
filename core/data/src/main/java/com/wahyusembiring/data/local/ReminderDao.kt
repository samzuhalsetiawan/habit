package com.wahyusembiring.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminder")
    fun getAllReminderAsFlow(): Flow<List<Reminder>>

    @Insert(
        entity = Reminder::class,
        onConflict = OnConflictStrategy.ABORT
    )
    suspend fun insertReminder(reminder: Reminder): Long

    @Update(entity = Reminder::class)
    suspend fun updateReminder(reminder: Reminder)

    @Upsert(entity = Attachment::class)
    suspend fun upsertAttachment(attachment: Attachment): Long

    @Upsert(entity = Attachment::class)
    suspend fun upsertAttachment(attachments: List<Attachment>): List<Long>

    @Insert(
        entity = ReminderAttachmentCrossRef::class,
        onConflict = OnConflictStrategy.ABORT
    )
    suspend fun insertExamWithAttachment(crosRef: ReminderAttachmentCrossRef)

    @Transaction
    suspend fun insertReminderWithAttachments(
        reminder: Reminder,
        attachments: List<Attachment>
    ) {
        val reminderId = insertReminder(reminder)
        for (attachment in attachments) {
            upsertAttachment(attachment)
            insertExamWithAttachment(ReminderAttachmentCrossRef(reminderId.toInt(), attachment.uri))
        }
    }
}