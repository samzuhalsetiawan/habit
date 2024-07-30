package com.wahyusembiring.habit.feature.subject.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.wahyusembiring.habit.feature.subject.domain.model.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

   @Query("SELECT * FROM subject")
   fun getAllSubjectsAsFlow(): Flow<List<Subject>>

   @Upsert(entity = Subject::class)
   suspend fun upsertSubject(subject: Subject): Long

}