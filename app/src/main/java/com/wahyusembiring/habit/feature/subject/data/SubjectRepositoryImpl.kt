package com.wahyusembiring.habit.feature.subject.data

import com.wahyusembiring.habit.feature.subject.data.local.SubjectDao
import com.wahyusembiring.habit.feature.subject.domain.model.Subject
import com.wahyusembiring.habit.feature.subject.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
   private val subjectDao: SubjectDao
) : SubjectRepository {
   override suspend fun getAllSubjectsAsFlow(): Flow<List<Subject>> {
      return subjectDao.getAllSubjectsAsFlow()
   }

   override suspend fun saveSubject(subject: Subject) {
      subjectDao.upsertSubject(subject)
   }
}