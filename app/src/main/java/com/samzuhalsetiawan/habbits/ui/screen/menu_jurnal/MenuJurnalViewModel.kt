package com.samzuhalsetiawan.habbits.ui.screen.menu_jurnal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.repository.MainRepositoryResult
import com.samzuhalsetiawan.habbits.utils.getCurrentLocalDateString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuJurnalViewModel(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _state = MutableStateFlow(
        State(
            currentDate =  getCurrentLocalDateString()
        )
    )
    val state = _state.asStateFlow()

    init {
        when (val result = mainRepository.getAllHabitAsFlow()) {
            is MainRepositoryResult.Failed -> {
                _state.update { oldState ->
                    oldState.copy(errorMessage = result.errorMessage)
                }
            }
            is MainRepositoryResult.Success -> {
                viewModelScope.launch {
                    result.data.collectLatest {
                        _state.update { oldState ->
                            oldState.copy(
                                errorMessage = null,
                                habits = it
                            )
                        }
                    }
                }
            }
        }
    }

    fun deleteHabit(habitId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteHabit(habitId)
        }
    }

    data class State(
        val currentDate: String,
        val habits: List<Habit> = emptyList(),
        val errorMessage: String? = null
    )

}