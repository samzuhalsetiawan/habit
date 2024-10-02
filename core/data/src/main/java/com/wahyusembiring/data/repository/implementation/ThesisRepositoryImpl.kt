package com.wahyusembiring.data.repository.implementation

import com.wahyusembiring.data.local.dao.TaskDao
import com.wahyusembiring.data.local.dao.ThesisDao
import com.wahyusembiring.data.model.entity.Task
import com.wahyusembiring.data.model.entity.Thesis
import com.wahyusembiring.data.model.ThesisWithTask
import com.wahyusembiring.data.repository.ThesisRepository
import kotlinx.coroutines.flow.Flow

class ThesisRepositoryImpl(
    private val thesisDao: ThesisDao,
    private val taskDao: TaskDao,
) : ThesisRepository {

    override fun getAllThesis(): Flow<List<ThesisWithTask>> {
        return thesisDao.getAllThesis()
    }

    override fun getThesisById(id: Int): Flow<ThesisWithTask> {
        return thesisDao.getThesisById(id)
    }

    override suspend fun saveNewThesis(thesis: Thesis): Long {
        return thesisDao.insertThesis(thesis)
    }

    override suspend fun updateThesis(thesis: Thesis) {
        return thesisDao.updateThesis(thesis)
    }

    override suspend fun updateThesisTitleById(id: Int, title: String) {
        return thesisDao.updateThesisTitleById(id, title)
    }

    override suspend fun deleteThesis(thesis: Thesis) {
        return thesisDao.deleteThesis(thesis)
    }

    override suspend fun addNewTask(task: Task): Long {
        return taskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        return taskDao.deleteTask(task)
    }

    override suspend fun changeTaskCompletedStatus(task: Task, isCompleted: Boolean) {
        return taskDao.updateTask(task.copy(isCompleted = isCompleted))
    }

}