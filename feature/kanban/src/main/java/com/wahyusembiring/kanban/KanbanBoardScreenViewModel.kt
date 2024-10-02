package com.wahyusembiring.kanban

import androidx.lifecycle.ViewModel
import com.wahyusembiring.data.repository.EventRepository
import com.wahyusembiring.data.repository.ExamRepository
import com.wahyusembiring.data.repository.HomeworkRepository
import com.wahyusembiring.data.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class KanbanBoardScreenViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _state = MutableStateFlow(KanbanBoardScreenUIState())
    val state = _state.asStateFlow()

    fun onUIEvent(event: KanbanBoardScreenUIEvent) {

    }

}