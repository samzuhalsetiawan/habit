package com.wahyusembiring.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wahyusembiring.data.model.LecturerWithSubject
import com.wahyusembiring.data.model.entity.Lecturer
import kotlinx.coroutines.flow.Flow

@Dao
interface LecturerDao {

    @Query("SELECT * FROM lecturer")
    fun getAllLecturer(): Flow<List<Lecturer>>

    @Query("SELECT * FROM lecturer")
    fun getAllLecturerWithSubject(): Flow<List<LecturerWithSubject>>

    @Insert
    suspend fun insertLecturer(lecturer: Lecturer): Long

}