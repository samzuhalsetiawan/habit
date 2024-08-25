package com.wahyusembiring.data.repository

import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Homework
import kotlinx.coroutines.flow.Flow

interface HomeworkRepository {

    fun getAllHomeworkAsFlow(): Flow<List<Homework>>

    suspend fun saveHomework(
        homework: Homework,
        attachments: List<Attachment>
    )

    suspend fun updateHomework(homework: Homework)

}