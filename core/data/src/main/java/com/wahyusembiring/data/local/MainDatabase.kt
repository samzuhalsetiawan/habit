package com.wahyusembiring.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wahyusembiring.data.local.dao.ExamDao
import com.wahyusembiring.data.local.dao.HomeworkDao
import com.wahyusembiring.data.local.dao.ReminderDao
import com.wahyusembiring.data.local.dao.SubjectDao
import com.wahyusembiring.data.local.dao.TaskDao
import com.wahyusembiring.data.local.dao.ThesisDao
import com.wahyusembiring.data.model.entity.Exam
import com.wahyusembiring.data.model.entity.Homework
import com.wahyusembiring.data.model.entity.Lecture
import com.wahyusembiring.data.model.entity.Reminder
import com.wahyusembiring.data.model.entity.Subject
import com.wahyusembiring.data.model.entity.Task
import com.wahyusembiring.data.model.entity.Thesis

@Database(
    entities = [
        Homework::class,
        Subject::class,
        Lecture::class,
        Exam::class,
        Reminder::class,
        Thesis::class,
        Task::class
    ],
    version = 13,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract val homeworkDao: HomeworkDao
    abstract val subjectDao: SubjectDao
    abstract val examDao: ExamDao
    abstract val reminderDao: ReminderDao
    abstract val thesisDao: ThesisDao
    abstract val taskDao: TaskDao

    companion object {
        private const val DATABASE_NAME = "habit.db"

        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getSingleton(appContext: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    appContext,
                    MainDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration(true)
                    .addTypeConverter(Converter(appContext))
                    .build().also { INSTANCE = it }
            }
        }

    }

}