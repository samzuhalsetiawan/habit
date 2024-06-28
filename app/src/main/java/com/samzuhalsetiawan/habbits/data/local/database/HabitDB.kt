package com.samzuhalsetiawan.habbits.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samzuhalsetiawan.habbits.data.local.dao.HabitDao
import com.samzuhalsetiawan.habbits.data.local.dao.StartingQuestionDao
import com.samzuhalsetiawan.habbits.data.local.entities.HabitEntity
import com.samzuhalsetiawan.habbits.data.local.entities.HabitHistoryEntity
import com.samzuhalsetiawan.habbits.data.local.entities.StartingQuestion

@Database(
    entities = [
        StartingQuestion::class,
        HabitEntity::class,
        HabitHistoryEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class HabitDB: RoomDatabase() {

    abstract val startingQuestionDao: StartingQuestionDao
    abstract val habitDao: HabitDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDB? = null

        fun getInstance(applicationContext: Context): HabitDB {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(applicationContext, HabitDB::class.java, "habit.db")
                    .fallbackToDestructiveMigration(true)
                    .build().also { INSTANCE = it }
            }
        }
    }
}