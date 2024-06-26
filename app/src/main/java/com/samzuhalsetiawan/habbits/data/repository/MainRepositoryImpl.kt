package com.samzuhalsetiawan.habbits.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.samzuhalsetiawan.habbits.data.local.database.HabitDB
import com.samzuhalsetiawan.habbits.data.local.entities.HabitEntity
import com.samzuhalsetiawan.habbits.data.local.entities.HabitHistoryEntity
import com.samzuhalsetiawan.habbits.data.local.entities.StartingQuestion
import com.samzuhalsetiawan.habbits.exceptions.NullErrorMessageException
import com.samzuhalsetiawan.habbits.models.AnsweredQuestion
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.models.HabitHistory
import com.samzuhalsetiawan.habbits.preferences.AppSettings
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.repository.MainRepositoryResult
import com.samzuhalsetiawan.habbits.utils.toHabit
import com.samzuhalsetiawan.habbits.utils.toHabitEntity
import com.samzuhalsetiawan.habbits.utils.toHabitHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID

class MainRepositoryImpl(
    private val habitDB: HabitDB,
    private val appSettingsDS: DataStore<AppSettings>
): MainRepository {

    override suspend fun deleteHabit(habitId: Int): MainRepositoryResult<Unit> {
        return handleRequestAsync {
            habitDB.habitDao.deleteHabitById(habitId)
        }
    }

    override suspend fun markHabitStatus(
        habitId: Int,
        status: HabitHistory.Status
    ): MainRepositoryResult<Unit> {
        return handleRequestAsync {
            val habitHistoryEntity = HabitHistoryEntity(
                habitId = habitId,
                status = status.name,
                dateCreated = System.currentTimeMillis()
            )
            habitDB.habitDao.insertHabitHistory(habitHistoryEntity)
        }
    }

    override fun getDetailHabit(habitId: Int): MainRepositoryResult<Flow<Map<Habit, List<HabitHistory>>>> {
        return handleRequest {
            habitDB.habitDao.getHabitWithHistory(habitId).map { maps ->
                buildMap {
                    maps.forEach { (habitEntity, habitHistoryEntities) ->
                        put(habitEntity.toHabit(), habitHistoryEntities.map { it.toHabitHistory() })
                    }
                }
            }
        }
    }

    override fun getAllHabitWithHistories(): MainRepositoryResult<Flow<Map<Habit, List<HabitHistory>>>> {
        return handleRequest {
            habitDB.habitDao.getAllHabitWithHistory().map { maps ->
                buildMap {
                    maps.forEach { (habitEntity, habitHistoryEntities) ->
                        put(habitEntity.toHabit(), habitHistoryEntities.map { it.toHabitHistory() })
                    }
                }
            }
        }
    }

    override fun getAllHabitAsFlow(): MainRepositoryResult<Flow<List<Habit>>> {
        return handleRequest {
            habitDB.habitDao.getAllHabit().map { list ->
                list.map { it.toHabit() }
            }
        }
    }

    override suspend fun saveNewHabit(habit: Habit): MainRepositoryResult<Unit> {
        return handleRequestAsync {
            habitDB.habitDao.insertHabit(habit = habit.toHabitEntity())
        }
    }

    override fun getIsNewUserFlow(): Flow<Boolean> {
        return appSettingsDS.data.map { it.isNewUser }
    }

    override fun getIsLoginFlow(): Flow<Boolean> {
        return appSettingsDS.data.map { it.isLogin }
    }

    override suspend fun setIsLogin(isLogin: Boolean): MainRepositoryResult<Unit> {
        return handleRequestAsync {
            appSettingsDS.updateData {
                it.copy(isLogin = isLogin)
            }
        }
    }

    override suspend fun saveAnsweredQuestion(answeredQuestion: AnsweredQuestion): MainRepositoryResult<Unit> {
        return handleRequestAsync {
            answeredQuestion.let {
                habitDB.startingQuestionDao.insertQuestionResult(StartingQuestion(it.question, it.answer, it.score))
            }
        }
    }

    override suspend fun saveAnsweredQuestion(answeredQuestions: List<AnsweredQuestion>): MainRepositoryResult<Unit> {
        return handleRequestAsync {
            habitDB.startingQuestionDao.insertMultipleQuestionResult(
                answeredQuestions.map { StartingQuestion(it.question, it.answer, it.score) }
            )
        }
    }

    override suspend fun setIsNewUser(isNewUser: Boolean): MainRepositoryResult<Unit> {
        return handleRequestAsync {
            appSettingsDS.updateData {
                it.copy(isNewUser = isNewUser)
            }
        }
    }

    override suspend fun getIsNewUser(): MainRepositoryResult<Boolean> {
        return handleRequestAsync {
            appSettingsDS.data.first().isNewUser
        }
    }

    private suspend fun <T> handleRequestAsync(onError: (suspend (throwable: Throwable) -> Unit)? = null, action: suspend () -> T): MainRepositoryResult<T> {
        return try {
            MainRepositoryResult.Success(action())
        } catch (throwable: Throwable) {
            onError?.invoke(throwable)
            Log.e(TAG, throwable.message, throwable)
            throwable.printStackTrace()
            MainRepositoryResult.Failed(throwable.message ?: throw NullErrorMessageException())
        }
    }

    private fun <T> handleRequest(onError: ((throwable: Throwable) -> Unit)? = null, action: () -> T): MainRepositoryResult<T> {
        return try {
            MainRepositoryResult.Success(action())
        } catch (throwable: Throwable) {
            onError?.invoke(throwable)
            Log.e(TAG, throwable.message, throwable)
            throwable.printStackTrace()
            MainRepositoryResult.Failed(throwable.message ?: throw NullErrorMessageException())
        }
    }

    companion object {
        private const val TAG = "RepositoryImpl"
    }
}