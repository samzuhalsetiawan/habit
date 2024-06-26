package com.samzuhalsetiawan.habbits.ui.screen.menu_progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.models.HabitHistory
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.repository.MainRepositoryResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuProgressViewModel(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = mainRepository.getAllHabitWithHistories()) {
                is MainRepositoryResult.Success -> {
                    result.data.map { maps ->
                        maps.map {
                            Pair(
                                it.key, it.value
                            )
                        }
                    }.collect { habitWithHistories ->
                        _state.update {
                            it.copy(habitsWithHistories = habitWithHistories)
                        }
                    }
                }
                is MainRepositoryResult.Failed -> {
                    throw Error(result.errorMessage)
                }
            }
        }
    }

    data class State(
        val habitsWithHistories: List<Pair<Habit, List<HabitHistory>>> = emptyList()
    )

}