package com.wahyusembiring.data.repository

import com.wahyusembiring.data.model.LecturerWithSubject
import com.wahyusembiring.data.model.entity.Lecturer
import kotlinx.coroutines.flow.Flow

interface LecturerRepository {

    fun getAllLecture(): Flow<List<Lecturer>>

    fun getAllLectureWithSubjects(): Flow<List<LecturerWithSubject>>

    suspend fun insertLecture(lecture: Lecturer): Long

}