package com.wahyusembiring.habit.feature.homework.domain.repository

import com.wahyusembiring.habit.core.domain.model.Attachment
import com.wahyusembiring.habit.feature.homework.domain.model.Homework
import kotlinx.coroutines.flow.Flow

interface HomeworkRepository {

   suspend fun getAllHomeworkAsFlow(): Flow<List<Homework>>

   suspend fun saveHomework(homework: Homework, attachments: List<Attachment>)

}