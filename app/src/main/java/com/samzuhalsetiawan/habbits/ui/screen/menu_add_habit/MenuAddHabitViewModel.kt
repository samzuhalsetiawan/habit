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
import com.samzuhalsetiawan.habbits.utils.dateFromHourAndMinute
import com.samzuhalsetiawan.habbits.utils.formatToTimeString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.util.Calendar
import java.util.Date

class MenuAddHabitViewModel(
   private val mainRepository: MainRepository
) : ViewModel(), MenuAddHabitAction {

   private val _state = MutableStateFlow(State())
   val state: StateFlow<State> = _state

   override fun onSetHabitName(name: String) {
      _state.update {
         it.copy(habitName = name)
      }
   }

   override fun onSetIsNoDayLimit(isForever: Boolean) {
      _state.update {
         it.copy(isNoDayLimit = isForever)
      }
   }

   override fun onSetEverydayReminder(isForever: Boolean) {
      _state.update {
         it.copy(everydayReminder = isForever)
      }
   }

   override fun onSetHabitType(type: Habit.Types) {
      _state.update {
         it.copy(habitType = type)
      }
   }

   override fun onDatePickerRangeSet(rentangWaktu: LongRange) {
      _state.update {
         it.copy(
            dayStart = rentangWaktu.first,
            dayEnd = rentangWaktu.last
         )
      }
   }

   override fun onTimePickerConfirm(hour: Int, minute: Int, days: List<DayOfWeek>, key: Int?) {
      val date = dateFromHourAndMinute(hour, minute)
      key?.let {
         _state.update {
            it.copy(
               reminders = ArrayList(it.reminders).toList().apply {
                  get(key).apply {
                     this.hour = hour
                     this.minute = minute
                     time = date.time
                     formattedTime = date.formatToTimeString()
                     this.days = days
                  }
               }
            )
         }
      } ?: _state.update {
         it.copy(
            reminders = ArrayList(it.reminders).apply {
               add(
                  Reminder(
                     hour = hour,
                     minute = minute,
                     time = date.time,
                     formattedTime = date.formatToTimeString(),
                     days = days
                  )
               )
            }.toList()
         )
      }
   }

   fun saveHabit(onNavigateToHome: () -> Unit) {
      val newHabit = Habit(
         id = 0,
         name = _state.value.habitName,
         isGoodHabit = true,
         dayStart = _state.value.dayStart,
         dayEnd = _state.value.dayEnd,
         isEndless = _state.value.isNoDayLimit,
         repeatEveryday = _state.value.everydayReminder,
         types = _state.value.habitType,
         reminders = _state.value.reminders
      )
      println(newHabit.reminders.size)
      viewModelScope.launch(Dispatchers.IO) {
         _state.update {
            it.copy(
               dialog = PopUpDialog.Loading
            )
         }
         delay(2000L)
         when (val result = mainRepository.saveNewHabit(newHabit)) {
            is MainRepositoryResult.Success -> {
               _state.update {
                  it.copy(
                     snackBarMessage = "Sukses",
                     dialog = null,
                     navigateToHome = true
                  )
               }
               withContext(Dispatchers.Main) {
                  onNavigateToHome()
               }
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

   override fun onShowTimePicker() {
      _state.update {
         it.copy(
            dialog = PopUpDialog.DailyReminderTimePicker(
               title = "Pilih Waktu",
               positiveButtonText = "Ok",
               negaviteButtonText = "Cancel",
               callback = { result ->
                  if (result.response == PopUpDialogResponse.POSITIVE && result.data != null) {
                     onTimePickerConfirm(
                        hour = result.data.hour,
                        minute = result.data.minute,
                        days = result.data.days
                     )
                  }
               }
            )
         )
      }
   }

   fun onSnackbarResult(snackbarResult: SnackbarResult) {
      println("onSnackbarResult")
      _state.update {
         it.copy(snackBarMessage = null)
      }
   }

   data class State(
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