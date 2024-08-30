package com.wahyusembiring.overview

import androidx.compose.ui.res.stringResource
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
import com.wahyusembiring.datetime.formatter.FormattingStyle
import com.wahyusembiring.overview.component.eventcard.EventCard
import com.wahyusembiring.overview.util.inside
import com.wahyusembiring.overview.util.until
import com.wahyusembiring.ui.util.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
                _state.update { state ->
                    state.copy(
                        eventCards = List(6) {
                            val currentMoment = Moment.now() + it.days
                            EventCard(
                                title = when (it) {
                                    0 -> UIText.StringResource(R.string.today)
                                    1 -> UIText.StringResource(R.string.tomorrow)
                                    else -> UIText.DynamicString(currentMoment.day.dayOfWeek)
                                },
                                date = when (it) {
                                    0, 1 -> UIText.DynamicString(
                                        currentMoment.toString(
                                            FormattingStyle.INDO_FULL
                                        )
                                    )

                                    else -> UIText.DynamicString(
                                        currentMoment.toString(
                                            FormattingStyle.INDO_MEDIUM
                                        )
                                    )
                                },
                                events = events inside (it.days until (it + 1).days)
                            )
                        }
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

            else -> Unit
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