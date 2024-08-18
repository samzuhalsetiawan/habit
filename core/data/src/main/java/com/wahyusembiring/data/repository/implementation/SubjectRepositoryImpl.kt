package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.data.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
   private val subjectDao: com.wahyusembiring.data.local.SubjectDao
) : SubjectRepository {
   override suspend fun getAllSubjectsAsFlow(): Flow<List<Subject>> {
      return subjectDao.getAllSubjectsAsFlow()
   }

   override suspend fun saveSubject(subject: Subject) {
      subjectDao.upsertSubject(subject)
   }
}