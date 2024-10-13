package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.local.dao.TaskDao
import com.wahyusembiring.data.local.dao.ThesisDao
import com.wahyusembiring.data.model.entity.Task
import com.wahyusembiring.data.model.entity.Thesis
import com.wahyusembiring.data.model.ThesisWithTask
import com.wahyusembiring.data.remote.ThesisService
import com.wahyusembiring.data.repository.ThesisRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThesisRepositoryImpl @Inject constructor(
    private val thesisDao: ThesisDao,
    private val taskDao: TaskDao,
    private val thesisService: ThesisService
) : ThesisRepository {

    override fun getAllThesis(): Flow<List<ThesisWithTask>> {
        return thesisDao.getAllThesis()
    }

    override fun getThesisById(id: Int): Flow<ThesisWithTask> {
        return thesisDao.getThesisById(id)
    }

    override suspend fun saveNewThesis(thesis: Thesis): Long {
        val thesisId = thesisDao.insertThesis(thesis)
        thesisService.saveNewThesis(thesis.copy(id = thesisId.toInt()))
        return thesisId
    }

    override suspend fun updateThesis(thesis: Thesis) {
        thesisDao.updateThesis(thesis)
        thesisService.updateThesis(thesis)
    }

    override suspend fun updateThesisTitleById(id: Int, title: String) {
        thesisDao.updateThesisTitleById(id, title)
        thesisService.updateThesisTitleById(id, title)
    }

    override suspend fun deleteThesis(thesis: Thesis) {
        thesisDao.deleteThesis(thesis)
        thesisService.deleteThesis(thesis)
    }

    override suspend fun addNewTask(task: Task): Long {
        val taskId = taskDao.insertTask(task)
        thesisService.addNewTask(task.copy(id = taskId.toInt()))
        return taskId
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
        thesisService.deleteTask(task)
    }

    override suspend fun changeTaskCompletedStatus(task: Task, isCompleted: Boolean) {
        taskDao.updateTask(task.copy(isCompleted = isCompleted))
        thesisService.changeTaskCompletedStatus(task, isCompleted)
    }

}