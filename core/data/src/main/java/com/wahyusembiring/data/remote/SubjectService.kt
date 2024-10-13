package com.wahyusembiring.data.remote

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wahyusembiring.data.local.Converter
import com.wahyusembiring.data.model.SubjectWithExam
import com.wahyusembiring.data.model.entity.Subject
import com.wahyusembiring.data.remote.util.toHashMap
import com.wahyusembiring.data.remote.util.toSubject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SubjectService @Inject constructor(
    private val converter: Converter,
) {

    companion object {
        private const val SUBJECT_COLLECTION_ID = "subject"
    }

    private val db by lazy { Firebase.firestore }

    suspend fun getAllSubject(): List<Subject> {
        val querySnapshot = db.collection(SUBJECT_COLLECTION_ID).get().await()
        return querySnapshot.documents.map { it.toSubject(converter) }
    }

    suspend fun getSubjectById(id: Int): Subject {
        val document = db.collection(SUBJECT_COLLECTION_ID).document(id.toString()).get().await()
        return document.toSubject(converter)
    }

    suspend fun saveSubject(subject: Subject) {
        val newSubject = subject.toHashMap(converter)
        val document = db.collection(SUBJECT_COLLECTION_ID).document(subject.id.toString())
        document
            .set(newSubject)
            .await()
    }

}