package com.wahyusembiring.habit.di

import android.app.Application
import com.wahyusembiring.data.local.MainDatabase
import com.wahyusembiring.data.local.HomeworkDao
import com.wahyusembiring.data.local.SubjectDao
import com.wahyusembiring.data.repository.implementation.HomeworkRepositoryImpl
import com.wahyusembiring.data.repository.HomeworkRepository
import com.wahyusembiring.data.repository.SubjectRepository
import com.wahyusembiring.data.repository.implementation.SubjectRepositoryImpl
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