package com.samzuhalsetiawan.habbits.repository

import android.content.Context
import com.samzuhalsetiawan.habbits.HabitAlarmManager
import com.samzuhalsetiawan.habbits.appSettingsDS
import com.samzuhalsetiawan.habbits.data.local.database.HabitDB
import com.samzuhalsetiawan.habbits.data.repository.MainRepositoryImpl
import com.samzuhalsetiawan.habbits.models.AnsweredQuestion
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.models.HabitHistory
import kotlinx.coroutines.flow.Flow

interface MainRepository {

   suspend fun saveAnsweredQuestion(answeredQuestion: AnsweredQuestion): MainRepositoryResult<Unit>

   suspend fun saveAnsweredQuestion(answeredQuestions: List<AnsweredQuestion>): MainRepositoryResult<Unit>

   suspend fun setIsNewUser(isNewUser: Boolean): MainRepositoryResult<Unit>

   suspend fun getIsNewUser(): MainRepositoryResult<Boolean>

   fun getIsNewUserFlow(): Flow<Boolean>

   fun getIsLoginFlow(): Flow<Boolean>

   suspend fun setIsLogin(isLogin: Boolean): MainRepositoryResult<Unit>

   fun getAllHabitAsFlow(): MainRepositoryResult<Flow<List<Habit>>>

   suspend fun saveNewHabit(habit: Habit): MainRepositoryResult<Unit>

   fun getAllHabitWithHistories(): MainRepositoryResult<Flow<Map<Habit, List<HabitHistory>>>>

   fun getDetailHabit(habitId: Int): MainRepositoryResult<Flow<Map<Habit, List<HabitHistory>>>>

   suspend fun markHabitStatus(
      habitId: Int,
      status: HabitHistory.Status
   ): MainRepositoryResult<Unit>

   suspend fun deleteHabit(habitId: Int): MainRepositoryResult<Unit>

   companion object {
      @Volatile
      private var INSTANCE: MainRepository? = null

      fun getInstance(applicationContext: Context): MainRepository {
         return INSTANCE ?: synchronized(this) {
            MainRepositoryImpl(
               habitDB = HabitDB.getInstance(applicationContext),
               appSettingsDS = applicationContext.appSettingsDS,
               habitAlarmManager = HabitAlarmManager(applicationContext)
            )
         }
      }
   }
}