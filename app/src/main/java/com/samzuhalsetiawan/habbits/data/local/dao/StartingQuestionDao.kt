package com.samzuhalsetiawan.habbits.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samzuhalsetiawan.habbits.data.local.entities.StartingQuestion

@Dao
interface StartingQuestionDao {

    @Query("SELECT * FROM starting_question_table")
    fun getAllQuestions(): List<StartingQuestion>

    @Insert(entity = StartingQuestion::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionResult(question: StartingQuestion)

    @Insert(entity = StartingQuestion::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleQuestionResult(questions: List<StartingQuestion>)

}