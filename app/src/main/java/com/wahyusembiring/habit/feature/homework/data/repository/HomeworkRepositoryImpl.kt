package com.wahyusembiring.habit.feature.homework.data.repository

import com.wahyusembiring.habit.core.domain.model.Attachment
import com.wahyusembiring.habit.feature.homework.data.local.HomeworkDao
import com.wahyusembiring.habit.feature.homework.domain.model.Homework
import com.wahyusembiring.habit.feature.homework.domain.repository.HomeworkRepository
import kotlinx.coroutines.flow.Flow

class HomeworkRepositoryImpl(
   private val homeworkDao: HomeworkDao
) : HomeworkRepository {

   override suspend fun getAllHomeworkAsFlow(): Flow<List<Homework>> {
      return homeworkDao.getAllHomeworkAsFlow()
   }

   override suspend fun saveHomework(homework: Homework, attachments: List<Attachment>) {
      if (attachments.isEmpty()) {
         homeworkDao.insertHomework(homework)
      } else {
         homeworkDao.insertHomeworkWithAttachments(homework, attachments)
      }
   }
}