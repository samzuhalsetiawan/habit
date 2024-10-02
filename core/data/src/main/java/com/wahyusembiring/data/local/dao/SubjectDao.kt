package com.wahyusembiring.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.wahyusembiring.data.model.SubjectWithExam
import com.wahyusembiring.data.model.SubjectWithExamAndHomework
import com.wahyusembiring.data.model.entity.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Transaction
    @Query("SELECT * FROM subject")
    fun getAllSubjectsAsFlow(): Flow<List<Subject>>

    @Upsert(entity = Subject::class)
    suspend fun upsertSubject(subject: Subject): Long

    @Transaction
    @Query("SELECT * FROM subject")
    fun getAllSubjectWithExam(): Flow<List<SubjectWithExam>>

    @Transaction
    @Query("SELECT * FROM subject, exam WHERE exam.date >= :minDate AND exam.date <= :maxDate")
    fun getAllSubjectWithExam(minDate: Long, maxDate: Long): Flow<List<SubjectWithExam>>

    @Transaction
    @Query("SELECT * FROM subject")
    fun getSubjectWithExamAndHomework(): Flow<List<SubjectWithExamAndHomework>>

    @Transaction
    @Query(
        "SELECT * " +
                "FROM subject, exam, homework " +
                "WHERE CASE WHEN :scored " +
                "THEN exam.score IS NOT NULL AND homework.score IS NOT NULL " +
                "ELSE exam.score IS NULL AND homework.score IS NULL " +
                "END"
    )
    fun getSubjectWithExamAndHomework(scored: Boolean): Flow<List<SubjectWithExamAndHomework>>
}