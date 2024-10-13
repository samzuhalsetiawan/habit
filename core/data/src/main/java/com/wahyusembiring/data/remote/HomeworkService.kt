package com.wahyusembiring.data.remote

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.wahyusembiring.data.local.Converter
import com.wahyusembiring.data.local.dao.HomeworkDao
import com.wahyusembiring.data.model.HomeworkWithSubject
import com.wahyusembiring.data.model.entity.Homework
import com.wahyusembiring.data.remote.util.toHashMap
import com.wahyusembiring.data.remote.util.toHomework
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeworkService @Inject constructor(
    private val converter: Converter,
    private val subjectService: SubjectService
) {

    companion object {
        private const val HOMEWORK_COLLECTION_ID = "homework"
    }

    private val db by lazy { Firebase.firestore }


    suspend fun getAllHomework(): List<Homework> {
        val querySnapshot = db.collection(HOMEWORK_COLLECTION_ID).get().await()
        return querySnapshot.documents.map { it.toHomework(converter) }
    }

    suspend fun getAllHomeworkWithSubject(): List<HomeworkWithSubject> {
        val homework = getAllHomework()
        return homework.map {
            HomeworkWithSubject(
                homework = it,
                subject = subjectService.getSubjectById(it.subjectId)
            )
        }
    }

    suspend fun saveHomework(homework: Homework) {
        val newHomework = homework.toHashMap(converter)
        val document = db.collection(HOMEWORK_COLLECTION_ID).document(homework.id.toString())
        document
            .set(newHomework)
            .await()
    }

    suspend fun deleteHomework(homework: Homework) {
        val document = db.collection(HOMEWORK_COLLECTION_ID).document(homework.id.toString())
        document
            .delete()
            .await()
    }

}