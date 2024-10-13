package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.local.dao.LecturerDao
import com.wahyusembiring.data.model.LecturerWithSubject
import com.wahyusembiring.data.model.entity.Lecturer
import com.wahyusembiring.data.remote.LecturerService
import com.wahyusembiring.data.repository.LecturerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LecturerRepositoryImpl @Inject constructor(
    private val lecturerDao: LecturerDao,
    private val lecturerService: LecturerService
) : LecturerRepository {

    override fun getAllLecture(): Flow<List<Lecturer>> {
        return lecturerDao.getAllLecturer()
    }

    override fun getAllLectureWithSubjects(): Flow<List<LecturerWithSubject>> {
        return lecturerDao.getAllLecturerWithSubject()
    }

    override suspend fun insertLecture(lecture: Lecturer): Long {
        val lecturerId = lecturerDao.insertLecturer(lecture)
        lecturerService.saveLecturer(lecture.copy(id = lecturerId.toInt()))
        return lecturerId
    }
}