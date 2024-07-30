package com.wahyusembiring.habit.feature.subject.domain.repository

import com.wahyusembiring.habit.feature.subject.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {

   suspend fun getAllSubjectsAsFlow(): Flow<List<Subject>>

   suspend fun saveSubject(subject: Subject)

}