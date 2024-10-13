package com.wahyusembiring.data.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wahyusembiring.data.local.Converter
import com.wahyusembiring.data.model.entity.Reminder
import com.wahyusembiring.data.remote.util.toHashMap
import com.wahyusembiring.data.remote.util.toReminder
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReminderService @Inject constructor(
    private val converter: Converter
) {

    companion object {
        private const val REMINDER_COLLECTION_ID = "reminder"
    }

    private val db by lazy { Firebase.firestore }

    suspend fun getAllReminder(): List<Reminder> {
        val querySnapshot = db.collection(REMINDER_COLLECTION_ID).get().await()
        return querySnapshot.documents.map { it.toReminder(converter) }
    }

    suspend fun saveReminder(reminder: Reminder) {
        val newReminder = reminder.toHashMap(converter)
        val document = db.collection(REMINDER_COLLECTION_ID).document(reminder.id.toString())
        document
            .set(newReminder)
            .await()
    }

    suspend fun deleteReminder(reminder: Reminder) {
        val document = db.collection(REMINDER_COLLECTION_ID).document(reminder.id.toString())
        document
            .delete()
            .await()
    }

}