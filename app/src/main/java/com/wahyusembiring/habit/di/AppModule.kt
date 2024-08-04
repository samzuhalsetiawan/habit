package com.wahyusembiring.habit.di

import android.app.Application
import android.content.Context
import com.wahyusembiring.habit.core.data.local.MainDatabase
import com.wahyusembiring.habit.feature.homework.data.local.HomeworkDao
import com.wahyusembiring.habit.feature.homework.data.repository.HomeworkRepositoryImpl
import com.wahyusembiring.habit.feature.homework.domain.repository.HomeworkRepository
import com.wahyusembiring.habit.feature.subject.data.SubjectRepositoryImpl
import com.wahyusembiring.habit.feature.subject.data.local.SubjectDao
import com.wahyusembiring.habit.feature.subject.domain.repository.SubjectRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

   @Provides
   @Singleton
   fun provideMainDatabase(application: Application): MainDatabase {
      return MainDatabase.getSingleton(application.applicationContext)
   }

   @Provides
   @Singleton
   fun provideHomeworkDao(mainDatabase: MainDatabase): HomeworkDao {
      return mainDatabase.homeworkDao
   }

   @Provides
   @Singleton
   fun provideHomeworkRepository(homeworkDao: HomeworkDao): HomeworkRepository {
      return HomeworkRepositoryImpl(homeworkDao)
   }

   @Provides
   @Singleton
   fun provideSubjectDao(mainDatabase: MainDatabase): SubjectDao {
      return mainDatabase.subjectDao
   }

   @Provides
   @Singleton
   fun provideSubjectRepository(subjectDao: SubjectDao): SubjectRepository {
      return SubjectRepositoryImpl(subjectDao)
   }

}