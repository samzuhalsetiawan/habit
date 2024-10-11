package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.local.dao.LectureDao
import com.wahyusembiring.data.model.LectureWithSubject
import com.wahyusembiring.data.model.entity.Lecture
import com.wahyusembiring.data.repository.LectureRepository
import kotlinx.coroutines.flow.Flow

class LectureRepositoryImpl(
    private val lectureDao: LectureDao
) : LectureRepository {

    override fun getAllLecture(): Flow<List<Lecture>> {
        return lectureDao.getAllLecture()
    }

    override fun getAllLectureWithSubjects(): Flow<List<LectureWithSubject>> {
        return lectureDao.getAllLectureWithSubjects()
    }

    override suspend fun insertLecture(lecture: Lecture): Long {
        return lectureDao.insertLecture(lecture)
    }
}