package com.wahyusembiring.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Homework
import com.wahyusembiring.data.model.Lecture
import com.wahyusembiring.data.model.Subject

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
               .fallbackToDestructiveMigration(true)
               .build().also { INSTANCE = it }
         }
      }

   }

}