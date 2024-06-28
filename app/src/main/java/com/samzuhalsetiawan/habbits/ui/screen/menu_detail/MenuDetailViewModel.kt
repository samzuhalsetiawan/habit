package com.samzuhalsetiawan.habbits.ui.screen.menu_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.models.HabitHistory
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.repository.MainRepositoryResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuDetailViewModel(
    private val mainRepository: MainRepository,
    private val habitId: Int
): ViewModel() {

    private val _state = MutableStateFlow(MenuDetailHabitState())
    val state: StateFlow<MenuDetailHabitState> = _state

    init {
      viewModelScope.launch(Dispatchers.IO) {
          launch { collectHabitDetailFlow(habitId) }
      }
    }

    private suspend fun collectHabitDetailFlow(habitId: Int) {
        when (val result = mainRepository.getDetailHabit(habitId)) {
            is MainRepositoryResult.Success -> {
                result.data.collect {
                    val entry = it.entries.first()
                    _state.emit(_state.value.copy(
                        habit = entry.key,
                        histories = entry.value
                    ))
                }
            }
            is MainRepositoryResult.Failed -> {
                throw Error(result.errorMessage)
            }
        }
    }

    fun markHabitStatus(habitId: Int, status: HabitHistory.Status) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = mainRepository.markHabitStatus(habitId, status)
            when (result) {
                is MainRepositoryResult.Failed -> {
                    throw Error(result.errorMessage)
                }
                is MainRepositoryResult.Success -> {
//                    TODO: Show dialog Completed
                    Unit
                }
            }
        }
    }

    data class MenuDetailHabitState(
        val habit: Habit = Habit(),
        val histories: List<HabitHistory> = emptyList()
    )
}