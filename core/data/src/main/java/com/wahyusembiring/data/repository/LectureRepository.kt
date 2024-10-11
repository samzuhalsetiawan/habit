package com.wahyusembiring.data.repository

import com.wahyusembiring.data.model.LectureWithSubject
import com.wahyusembiring.data.model.entity.Lecture
import kotlinx.coroutines.flow.Flow

interface LectureRepository {

    fun getAllLecture(): Flow<List<Lecture>>

    fun getAllLectureWithSubjects(): Flow<List<LectureWithSubject>>

    suspend fun insertLecture(lecture: Lecture): Long

}