package com.wahyusembiring.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Homework
import com.wahyusembiring.data.model.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeworkDao {

    @Insert(
        entity = Homework::class,
        onConflict = OnConflictStrategy.ABORT
    )
    suspend fun insertHomework(homework: Homework): Long

    @Update(entity = Homework::class)
    suspend fun updateHomework(homework: Homework)

    @Insert(
        entity = HomeworkAttachmentCrosRef::class,
        onConflict = OnConflictStrategy.ABORT
    )
    suspend fun insertHomeworkAttachment(crosRef: HomeworkAttachmentCrosRef)

    @Upsert(entity = Attachment::class)
    suspend fun upsertAttachment(attachment: Attachment): Long

    @Upsert(entity = Attachment::class)
    suspend fun upsertAttachment(attachments: List<Attachment>): List<Long>

    @Transaction
    suspend fun insertHomeworkWithAttachments(
        homework: Homework,
        attachments: List<Attachment>
    ) {
        val homeworkId = insertHomework(homework)
        for (attachment in attachments) {
            upsertAttachment(attachment)
            insertHomeworkAttachment(HomeworkAttachmentCrosRef(homeworkId.toInt(), attachment.uri))
        }
    }

    @Query(
        "SELECT * " +
                "FROM homework " +
                "JOIN subject " +
                "ON homework.subject = subject.id"
    )
    fun getAllHomeworkWithSubject(): Flow<Map<Homework, Subject>>

    @Query(
        "SELECT * " +
                "FROM homework " +
                "JOIN subject " +
                "ON homework.subject = subject.id " +
                "WHERE due_date >= :minDate AND due_date <= :maxDate " +
                "ORDER BY due_date ASC"
    )
    fun getAllHomeworkWithSubject(minDate: Long, maxDate: Long): Flow<Map<Homework, Subject>>

}