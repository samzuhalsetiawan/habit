package com.wahyusembiring.data.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wahyusembiring.data.local.Converter
import com.wahyusembiring.data.model.entity.Lecturer
import com.wahyusembiring.data.remote.util.toHashMap
import com.wahyusembiring.data.remote.util.toLecturer
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LecturerService @Inject constructor(
    private val converter: Converter
) {

    companion object {
        private const val LECTURER_COLLECTION_ID = "lecturer"
    }

    private val db by lazy { Firebase.firestore }

    suspend fun getLecturerById(id: Int): Lecturer {
        val document = db.collection(LECTURER_COLLECTION_ID).document(id.toString()).get().await()
        return document.toLecturer(converter)
    }

    suspend fun saveLecturer(lecturer: Lecturer) {
        val newLecturer = lecturer.toHashMap(converter)
        db.collection(LECTURER_COLLECTION_ID).document(lecturer.id.toString()).set(newLecturer)
            .await()
    }

}