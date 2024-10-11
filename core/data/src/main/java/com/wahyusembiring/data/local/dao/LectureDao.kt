package com.wahyusembiring.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wahyusembiring.data.model.LectureWithSubject
import com.wahyusembiring.data.model.entity.Lecture
import kotlinx.coroutines.flow.Flow

@Dao
interface LectureDao {

    @Query("SELECT * FROM lecture")
    fun getAllLecture(): Flow<List<Lecture>>

    @Query("SELECT * FROM lecture")
    fun getAllLectureWithSubjects(): Flow<List<LectureWithSubject>>

    @Insert
    suspend fun insertLecture(lecture: Lecture): Long

}