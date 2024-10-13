package com.wahyusembiring.data.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wahyusembiring.data.local.Converter
import com.wahyusembiring.data.model.ThesisWithTask
import com.wahyusembiring.data.model.entity.Task
import com.wahyusembiring.data.model.entity.Thesis
import com.wahyusembiring.data.remote.util.toHashMap
import com.wahyusembiring.data.repository.ThesisRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ThesisService @Inject constructor(
    private val converter: Converter
) {

    companion object {
        private const val THESIS_COLLECTION_ID = "thesis"
        private const val TASK_COLLECTION_ID = "task"
    }

    private val db by lazy { Firebase.firestore }

    suspend fun saveNewThesis(thesis: Thesis) {
        db.collection(THESIS_COLLECTION_ID).document(thesis.id.toString())
            .set(thesis.toHashMap(converter))
            .await()
    }

    suspend fun updateThesis(thesis: Thesis) {
        db.collection(THESIS_COLLECTION_ID).document(thesis.id.toString())
            .set(thesis.toHashMap(converter))
            .await()
    }

    suspend fun updateThesisTitleById(id: Int, title: String) {
        db.collection(THESIS_COLLECTION_ID).document(id.toString())
            .update("title", title)
            .await()
    }

    suspend fun deleteThesis(thesis: Thesis) {
        db.collection(THESIS_COLLECTION_ID).document(thesis.id.toString())
            .delete()
            .await()
    }

    suspend fun addNewTask(task: Task) {
        db.collection(TASK_COLLECTION_ID).document(task.id.toString())
            .set(task.toHashMap(converter))
            .await()
    }

    suspend fun deleteTask(task: Task) {
        db.collection(TASK_COLLECTION_ID).document(task.id.toString())
            .delete()
            .await()
    }

    suspend fun changeTaskCompletedStatus(task: Task, isCompleted: Boolean) {
        db.collection(TASK_COLLECTION_ID).document(task.id.toString())
            .update("is_completed", isCompleted)
            .await()
    }

}