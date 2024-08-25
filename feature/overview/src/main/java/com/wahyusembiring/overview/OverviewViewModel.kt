package com.wahyusembiring.overview

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.common.util.launch
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.data.repository.ExamRepository
import com.wahyusembiring.data.repository.HomeworkRepository
import com.wahyusembiring.data.repository.ReminderRepository
import com.wahyusembiring.overview.model.Event
import com.wahyusembiring.overview.model.EventType
import com.wahyusembiring.overview.model.toEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val homeworkRepository: HomeworkRepository,
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val examsFlow = examRepository.getAllExamsAsFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val homeworksFlow = homeworkRepository.getAllHomeworkAsFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val remindersFlow = reminderRepository.getRemindersAsFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val eventsFlow =
        combine(examsFlow, homeworksFlow, remindersFlow) { exams, homeworks, reminders ->
            val dummySubject = Subject(
                name = "Dummy",
                color = Color.Blue,
                room = "Dummy Room",
                lecture = null,
                description = ""
            )
            exams.map { it.toEvent(dummySubject) } + homeworks.map { it.toEvent(dummySubject) } + reminders.map {
                it.toEvent(
                    dummySubject
                )
            }
        }

    private val _state = MutableStateFlow(OverviewScreenUIState())
    val state: StateFlow<OverviewScreenUIState> = _state

    init {
        launch {
            eventsFlow.collect { events ->
                _state.update {
                    it.copy(todayEvents = events)
                }
            }
        }
    }

    fun onUIEvent(event: OverviewScreenUIEvent) {
        when (event) {
            is OverviewScreenUIEvent.OnMarkEventAsCompleted -> launch {
                onMarkEventAsCompleted(event.event)
            }

            is OverviewScreenUIEvent.OnMarkEventAsUncompleted -> launch {
                onMarkEventAsUncompleted(event.event)
            }
        }
    }

    private suspend fun onMarkEventAsCompleted(event: Event) {
        when (event.eventType) {
            EventType.EXAM -> {
                val exam = examsFlow.value.find { it.id == event.id }!!
                examRepository.updateExam(exam.copy(completed = true))
            }

            EventType.HOMEWORK -> {
                val homework = homeworksFlow.value.find { it.id == event.id }!!
                homeworkRepository.updateHomework(homework.copy(completed = true))
            }

            EventType.REMINDER -> {
                val reminder = remindersFlow.value.find { it.id == event.id }!!
                reminderRepository.updateReminder(reminder.copy(completed = true))
            }
        }
    }

    private suspend fun onMarkEventAsUncompleted(event: Event) {
        when (event.eventType) {
            EventType.EXAM -> {
                val exam = examsFlow.value.find { it.id == event.id }!!
                examRepository.updateExam(exam.copy(completed = false))
            }

            EventType.HOMEWORK -> {
                val homework = homeworksFlow.value.find { it.id == event.id }!!
                homeworkRepository.updateHomework(homework.copy(completed = false))
            }

            EventType.REMINDER -> {
                val reminder = remindersFlow.value.find { it.id == event.id }!!
                reminderRepository.updateReminder(reminder.copy(completed = false))
            }
        }
    }

    private fun showPopUp(popUp: OverviewScreenPopUp) {
        _state.update { it.copy(popUp = popUp) }
    }

    private fun hidePopUp(popUp: OverviewScreenPopUp) {
        _state.update { it.copy(popUp = null) }
    }

}