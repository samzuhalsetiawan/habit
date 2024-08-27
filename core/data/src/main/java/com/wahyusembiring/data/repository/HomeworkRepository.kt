package com.wahyusembiring.data.repository

import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Homework
import com.wahyusembiring.data.model.Subject
import kotlinx.coroutines.flow.Flow

interface HomeworkRepository {

    fun getAllHomework(
        minDate: Long? = null,
        maxDate: Long? = null
    ): Flow<Map<Homework, Subject>>

    suspend fun saveHomework(
        homework: Homework,
        attachments: List<Attachment>
    )

    suspend fun updateHomework(homework: Homework)

}