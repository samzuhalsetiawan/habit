package com.wahyusembiring.data.repository

import com.wahyusembiring.data.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {

   suspend fun getAllSubjectsAsFlow(): Flow<List<Subject>>

   suspend fun saveSubject(subject: Subject)

}