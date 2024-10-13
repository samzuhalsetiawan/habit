package com.wahyusembiring.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.wahyusembiring.data.model.ExamWithSubject
import com.wahyusembiring.data.model.entity.Exam
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {

    @Transaction
    @Query("SELECT * FROM exam")
    fun getAllExamWithSubject(): Flow<List<ExamWithSubject>>

    @Transaction
    @Query("SELECT * FROM exam WHERE id = :id")
    fun getExamById(id: Int): Flow<ExamWithSubject?>

    @Insert(entity = Exam::class)
    suspend fun insertExam(exam: Exam): Long

    @Update(entity = Exam::class)
    suspend fun updateExam(exam: Exam)

    @Delete(entity = Exam::class)
    suspend fun deleteExam(exam: Exam)

}