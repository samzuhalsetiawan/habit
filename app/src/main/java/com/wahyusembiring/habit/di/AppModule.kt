package com.wahyusembiring.habit.di

import android.app.Application
import com.wahyusembiring.data.local.MainDatabase
import com.wahyusembiring.data.local.dao.SubjectDao
import com.wahyusembiring.data.repository.DataStoreRepository
import com.wahyusembiring.data.repository.EventRepository
import com.wahyusembiring.data.repository.LectureRepository
import com.wahyusembiring.data.repository.SubjectRepository
import com.wahyusembiring.data.repository.ThesisRepository
import com.wahyusembiring.data.repository.implementation.DataStoreRepositoryImpl
import com.wahyusembiring.data.repository.implementation.EventRepositoryImpl
import com.wahyusembiring.data.repository.implementation.LectureRepositoryImpl
import com.wahyusembiring.data.repository.implementation.SubjectRepositoryImpl
import com.wahyusembiring.data.repository.implementation.ThesisRepositoryImpl
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
    fun provideSubjectRepository(mainDatabase: MainDatabase): SubjectRepository {
        return SubjectRepositoryImpl(
            subjectDao = mainDatabase.subjectDao
        )
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        mainDatabase: MainDatabase
    ): EventRepository {
        return EventRepositoryImpl(
            examDao = mainDatabase.examDao,
            homeworkDao = mainDatabase.homeworkDao,
            reminderDao = mainDatabase.reminderDao
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(application: Application): DataStoreRepository {
        return DataStoreRepositoryImpl(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideThesisRepository(mainDatabase: MainDatabase): ThesisRepository {
        return ThesisRepositoryImpl(
            taskDao = mainDatabase.taskDao,
            thesisDao = mainDatabase.thesisDao
        )
    }

    @Provides
    @Singleton
    fun provideLectureRepository(mainDatabase: MainDatabase): LectureRepository {
        return LectureRepositoryImpl(
            lectureDao = mainDatabase.lectureDao
        )
    }

}