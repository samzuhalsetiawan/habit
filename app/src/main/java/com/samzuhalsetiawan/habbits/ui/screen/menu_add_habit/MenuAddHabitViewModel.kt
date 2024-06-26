package com.samzuhalsetiawan.habbits.ui.screen.menu_add_habit

import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.models.Reminder
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.repository.MainRepositoryResult
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialogResponse
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.utils.formatToTimeString
import com.samzuhalsetiawan.habbits.utils.getCurrentLocalDateString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class MenuAddHabitViewModel(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    fun setHabitName(name: String) {
        _state.update {
            it.copy(habitName = name)
        }
    }

    fun setIsNoDayLimit(isForever: Boolean) {
        _state.update {
            it.copy(isNoDayLimit = isForever)
        }
    }

    fun setEveryDayReminder(isForever: Boolean) {
        _state.update {
            it.copy(everydayReminder = isForever)
        }
    }

    fun setHabitType(type: Habit.Types) {
        _state.update {
            it.copy(habitType = type)
        }
    }

    fun onDatePickerRangeSet(rentangWaktu: LongRange) {
        _state.update {
            it.copy(
                dayStart = rentangWaktu.first,
                dayEnd = rentangWaktu.last
            )
        }
    }

    fun onTimePickerConfirm(hour: Int, minute: Int, key: Int? = null) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.isLenient = false
        val date = cal.time
        key?.let { key ->
            _state.update {
                it.copy(
                    reminders = ArrayList(it.reminders).toList().apply {
                        get(key).apply {
                            time = date.time
                            formattedTime = date.formatToTimeString()
                        }
                    }
                )
            }
        } ?: _state.update {
            it.copy(
                reminders = ArrayList(it.reminders).apply {
                    add(Reminder(date.time, date.formatToTimeString()))
                }.toList()
            )
        }
    }

    fun saveHabit() {
        val newHabit = Habit(
            id = 0,
            name = _state.value.habitName,
            isGoodHabit = true,
            dayStart = _state.value.dayStart,
            dayEnd = _state.value.dayEnd,
            isEndless = _state.value.isNoDayLimit,
            repeatEveryday = _state.value.everydayReminder,
            types = _state.value.habitType
        )
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    dialog = PopUpDialog.Loading
                )
            }
            delay(3000L)
            when (val result = mainRepository.saveNewHabit(newHabit)) {
                is MainRepositoryResult.Success -> {
                    _state.update { it.copy(
                        snackBarMessage = "Sukses",
                        dialog = null,
                        navigateToHome = true
                    ) }
                }
                is MainRepositoryResult.Failed -> {
                    _state.update {
                        it.copy(
                            dialog = PopUpDialog.Error(
                                errorMessage = result.errorMessage
                            )
                        )
                    }
                }
            }
        }
    }

    fun onSnackbarResult(snackbarResult: SnackbarResult) {
        println("onSnackbarResult")
        _state.update {
            it.copy(snackBarMessage = null)
        }
    }

    data class State (
        val currentDate: Date = Date(),
        val dayStart: Long = 0L,
        val dayEnd: Long = 0L,
        val habitName: String = "",
        val isNoDayLimit: Boolean = false,
        val reminders: List<Reminder> = emptyList(),
        val everydayReminder: Boolean = false,
        val habitType: Habit.Types = Habit.Types.HEALTH,
        val snackBarMessage: String? = null,
        val dialog: PopUpDialog? = null,
        val navigateToHome: Boolean = false
    )
}