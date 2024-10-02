package com.wahyusembiring.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.data.repository.EventRepository
import com.wahyusembiring.data.repository.ExamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CalendarScreenUIState())
    val state = _state.asStateFlow()

    private val events = eventRepository.getAllEvent()

    init {
        viewModelScope.launch {
            events.collect {
                _state.update { uIstate ->
                    uIstate.copy(events = it)
                }
            }
        }
    }

    fun onUIEvent(event: CalendarScreenUIEvent) {

    }

}