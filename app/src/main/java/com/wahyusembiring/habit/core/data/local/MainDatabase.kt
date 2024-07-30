package com.wahyusembiring.habit.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wahyusembiring.habit.core.domain.model.Attachment
import com.wahyusembiring.habit.feature.homework.data.local.HomeworkAttachmentCrosRef
import com.wahyusembiring.habit.feature.homework.data.local.HomeworkDao
import com.wahyusembiring.habit.feature.homework.domain.model.Homework
import com.wahyusembiring.habit.feature.lecture.domain.model.Lecture
import com.wahyusembiring.habit.feature.subject.data.local.SubjectDao
import com.wahyusembiring.habit.feature.subject.domain.model.Subject

@Database(
   entities = [
      Homework::class,
      HomeworkAttachmentCrosRef::class,
      Attachment::class,
      Subject::class,
      Lecture::class
   ],
   version = 1,
   exportSchema = false
)
@TypeConverters(Converter::class)
abstract class MainDatabase : RoomDatabase() {

   abstract val homeworkDao: HomeworkDao
   abstract val subjectDao: SubjectDao

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
               .fallbackToDestructiveMigration()
               .build().also { INSTANCE = it }
         }
      }

   }

}