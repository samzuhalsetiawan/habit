package com.wahyusembiring.overview

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.common.util.launch
import com.wahyusembiring.data.model.Event
import com.wahyusembiring.data.model.Exam
import com.wahyusembiring.data.model.Homework
import com.wahyusembiring.data.model.Reminder
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.data.repository.ExamRepository
import com.wahyusembiring.data.repository.HomeworkRepository
import com.wahyusembiring.data.repository.ReminderRepository
import com.wahyusembiring.datetime.Moment
import com.wahyusembiring.overview.util.inside
import com.wahyusembiring.overview.util.until
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Clock
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val homeworkRepository: HomeworkRepository,
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val examsFlow = examRepository
        .getAllExam(
            minDate = Moment.now().epochMilliseconds,
            maxDate = (Moment.now() + 7.days).epochMilliseconds
        )
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyMap()
        )

    private val homeworksFlow = homeworkRepository
        .getAllHomework(
            minDate = Moment.now().epochMilliseconds,
            maxDate = (Moment.now() + 7.days).epochMilliseconds
        )
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyMap()
        )

    private val remindersFlow = reminderRepository
        .getAllReminder(
            minDate = Moment.now().epochMilliseconds,
            maxDate = (Moment.now() + 7.days).epochMilliseconds
        )
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val eventsFlow: Flow<Map<Event, Subject?>> =
        combine(examsFlow, homeworksFlow, remindersFlow) { exams, homeworks, reminders ->
            exams + homeworks + reminders.associateWith { null }
        }

    private val _state = MutableStateFlow(OverviewScreenUIState())
    val state: StateFlow<OverviewScreenUIState> = _state

    init {
        launch {
            eventsFlow.collect { events ->
                _state.update {
                    it.copy(
                        todayEvents = events inside (0.days until 1.days),
                        tomorrowEvents = events inside (1.days until 2.days),
                        next2DaysEvents = events inside (2.days until 3.days),
                        next3DaysEvents = events inside (3.days until 4.days),
                        next4DaysEvents = events inside (4.days until 5.days),
                        next5DaysEvents = events inside (5.days until 6.days),
                        next6DaysEvents = events inside (6.days until 7.days)
                    )
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
        when (event) {
            is Exam -> {
                examRepository.updateExam(event.copy(completed = true))
            }

            is Homework -> {
                homeworkRepository.updateHomework(event.copy(completed = true))
            }

            is Reminder -> {
                reminderRepository.updateReminder(event.copy(completed = true))
            }
        }
    }

    private suspend fun onMarkEventAsUncompleted(event: Event) {
        when (event) {
            is Exam -> {
                examRepository.updateExam(event.copy(completed = false))
            }

            is Homework -> {
                homeworkRepository.updateHomework(event.copy(completed = false))
            }

            is Reminder -> {
                reminderRepository.updateReminder(event.copy(completed = false))
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