package com.wahyusembiring.habit.di

import android.content.Context
import com.wahyusembiring.habit.core.data.local.MainDatabase
import com.wahyusembiring.habit.feature.homework.data.local.HomeworkDao
import com.wahyusembiring.habit.feature.homework.data.repository.HomeworkRepositoryImpl
import com.wahyusembiring.habit.feature.homework.domain.repository.HomeworkRepository
import com.wahyusembiring.habit.feature.subject.data.SubjectRepositoryImpl
import com.wahyusembiring.habit.feature.subject.data.local.SubjectDao
import com.wahyusembiring.habit.feature.subject.domain.repository.SubjectRepository

interface AppModule {
   val mainDatabase: MainDatabase
   val homeworkRepository: HomeworkRepository
   val homeworkDao: HomeworkDao
   val subjectRepository: SubjectRepository
   val subjectDao: SubjectDao
}

class AppModuleImpl(
   private val appContext: Context
) : AppModule {
   override val mainDatabase: MainDatabase by lazy {
      MainDatabase.getSingleton(appContext)
   }
   override val homeworkRepository: HomeworkRepository by lazy {
      HomeworkRepositoryImpl(homeworkDao)
   }
   override val homeworkDao: HomeworkDao by lazy {
      mainDatabase.homeworkDao
   }
   override val subjectDao: SubjectDao by lazy {
      mainDatabase.subjectDao
   }
   override val subjectRepository: SubjectRepository by lazy {
      SubjectRepositoryImpl(subjectDao)
   }
}