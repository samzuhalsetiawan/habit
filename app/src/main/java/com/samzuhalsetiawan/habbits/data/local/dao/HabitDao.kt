package com.samzuhalsetiawan.habbits.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.samzuhalsetiawan.habbits.data.local.entities.HabitEntity
import com.samzuhalsetiawan.habbits.data.local.entities.HabitHistoryEntity
import com.samzuhalsetiawan.habbits.data.local.entities.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

   @Query("SELECT * FROM habit_table")
   fun getAllHabit(): Flow<List<HabitEntity>>

   @Insert(entity = HabitEntity::class, OnConflictStrategy.REPLACE)
   suspend fun insertHabit(habit: HabitEntity): Long

   @Query(
      "SELECT * FROM habit_table " +
            "LEFT JOIN habit_history_table ON habit_table.id = habit_history_table.habit_id"
   )
   fun getAllHabitWithHistory(): Flow<Map<HabitEntity, List<HabitHistoryEntity>>>

   @Query(
      "SELECT * FROM habit_table " +
            "LEFT JOIN habit_history_table ON habit_table.id = habit_history_table.habit_id " +
            "WHERE habit_table.id = :habitId"
   )
   fun getHabitWithHistory(habitId: Int): Flow<Map<HabitEntity, List<HabitHistoryEntity>>>

   @Upsert(entity = HabitHistoryEntity::class)
   suspend fun insertHabitHistory(habitHistoryEntity: HabitHistoryEntity)

   @Delete(entity = HabitEntity::class)
   suspend fun deleteHabit(habit: HabitEntity)

   @Query("DELETE FROM habit_table WHERE id = :habitId")
   suspend fun deleteHabitById(habitId: Int)

   @Query(
      "SELECT history.status " +
            "FROM habit_history_table history " +
            "JOIN habit_table habit " +
            "ON history.habit_id = habit.id " +
            "WHERE history.habit_id = :habitId " +
            "ORDER BY history.date_created DESC LIMIT 1"
   )
   fun getLastHabitStatus(habitId: Int): String?

   @Query(
      "SELECT habit.streak " +
            "FROM habit_table habit " +
            "WHERE habit.id = :habitId LIMIT 1"
   )
   fun getStreakCountOf(habitId: Int): Int

   @Query(
      "UPDATE habit_table " +
            "SET streak = :streakCount " +
            "WHERE id = :habitId"
   )
   suspend fun updateStreakCountOf(habitId: Int, streakCount: Int)

   @Insert(entity = ReminderEntity::class, onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertReminder(reminder: ReminderEntity): Long
}