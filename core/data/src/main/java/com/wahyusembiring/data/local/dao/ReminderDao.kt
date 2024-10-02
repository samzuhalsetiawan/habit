package com.wahyusembiring.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.wahyusembiring.data.model.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminder")
    fun getAllReminder(): Flow<List<Reminder>>

    @Query("SELECT * FROM reminder WHERE id = :id")
    fun getReminderById(id: Int): Flow<Reminder?>

    @Query(
        "SELECT * " +
                "FROM reminder " +
                "WHERE date >= :minDate AND date <= :maxDate " +
                "ORDER BY date ASC"
    )
    fun getAllReminder(minDate: Long, maxDate: Long): Flow<List<Reminder>>

    @Upsert(entity = Reminder::class)
    suspend fun insertReminder(reminder: Reminder): Long

    @Update(entity = Reminder::class)
    suspend fun updateReminder(reminder: Reminder)

    @Delete(entity = Reminder::class)
    suspend fun deleteReminder(reminder: Reminder)

}