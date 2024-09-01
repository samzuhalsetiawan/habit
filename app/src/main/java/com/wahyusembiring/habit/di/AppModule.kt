package com.wahyusembiring.habit.di

import android.app.Application
import com.wahyusembiring.data.local.ExamDao
import com.wahyusembiring.data.local.MainDatabase
import com.wahyusembiring.data.local.HomeworkDao
import com.wahyusembiring.data.local.ReminderDao
import com.wahyusembiring.data.local.SubjectDao
import com.wahyusembiring.data.repository.DataStoreRepository
import com.wahyusembiring.data.repository.EventRepository
import com.wahyusembiring.data.repository.ExamRepository
import com.wahyusembiring.data.repository.implementation.HomeworkRepositoryImpl
import com.wahyusembiring.data.repository.HomeworkRepository
import com.wahyusembiring.data.repository.ReminderRepository
import com.wahyusembiring.data.repository.SubjectRepository
import com.wahyusembiring.data.repository.implementation.DataStoreRepositoryImpl
import com.wahyusembiring.data.repository.implementation.EventRepositoryImpl
import com.wahyusembiring.data.repository.implementation.ExamRepositoryImpl
import com.wahyusembiring.data.repository.implementation.ReminderRepositoryImpl
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

    @Provides
    @Singleton
    fun provideExamDao(mainDatabase: MainDatabase): ExamDao {
        return mainDatabase.examDao
    }

    @Provides
    @Singleton
    fun provideExamRepository(examDao: ExamDao): ExamRepository {
        return ExamRepositoryImpl(examDao)
    }

    @Provides
    @Singleton
    fun provideReminderDao(mainDatabase: MainDatabase): ReminderDao {
        return mainDatabase.reminderDao
    }

    @Provides
    @Singleton
    fun provideReminderRepository(reminderDao: ReminderDao): ReminderRepository {
        return ReminderRepositoryImpl(reminderDao)
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        examDao: ExamDao,
        homeworkDao: HomeworkDao,
        reminderDao: ReminderDao,
    ): EventRepository {
        return EventRepositoryImpl(examDao, homeworkDao, reminderDao)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(application: Application): DataStoreRepository {
        return DataStoreRepositoryImpl(application.applicationContext)
    }

}