package com.wahyusembiring.homework

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.common.util.scheduleReminder
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Time
import com.wahyusembiring.data.model.entity.Homework
import com.wahyusembiring.data.model.entity.Subject
import com.wahyusembiring.data.repository.EventRepository
import com.wahyusembiring.data.repository.HomeworkRepository
import com.wahyusembiring.data.repository.SubjectRepository
import com.wahyusembiring.ui.component.popup.AlertDialog
import com.wahyusembiring.ui.component.popup.Picker
import com.wahyusembiring.ui.component.popup.PopUp
import com.wahyusembiring.ui.util.UIText
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel(assistedFactory = CreateHomeworkScreenViewModel.Factory::class)
class CreateHomeworkScreenViewModel @AssistedInject constructor(
    @Assisted private val homeworkId: Int = -1,
    private val eventRepository: EventRepository,
    private val subjectRepository: SubjectRepository
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(homeworkId: Int = -1): CreateHomeworkScreenViewModel
    }

    private var getAllSubjectJob: Job? = null

    private val _state = MutableStateFlow(CreateHomeworkScreenUIState())
    val state = _state.asStateFlow()

    fun onUIEvent(event: CreateHomeworkUIEvent) {
        viewModelScope.launch {
            when (event) {
                is CreateHomeworkUIEvent.OnHomeworkTitleChanged -> onHomeworkTitleChanged(event.title)
                is CreateHomeworkUIEvent.OnSaveHomeworkButtonClicked -> onSaveHomeworkButtonClick(
                    event.context
                )

                is CreateHomeworkUIEvent.OnPickDateButtonClicked -> onDatePickerClick()
                is CreateHomeworkUIEvent.OnPickTimeButtonClicked -> onTimePickerClick()
                is CreateHomeworkUIEvent.OnPickSubjectButtonClicked -> onSubjectPickerClick()
                is CreateHomeworkUIEvent.OnPickAttachmentButtonClicked -> onAttachmentPickerClick()
                else -> Unit
            }
        }
    }

    private suspend fun onAttachmentPickerClick() {
        val attachmentPicker = Picker.AttachmentPicker()
        showPopUp(attachmentPicker)
        when (val result = attachmentPicker.result.await()) {
            is Picker.Result.Picked -> {
                onAttachmentsConfirmed(result.value)
                hidePopUp(attachmentPicker)
            }

            is Picker.Result.Dismiss -> hidePopUp(attachmentPicker)
        }
    }

    private suspend fun onSubjectPickerClick() {
        val subjectPicker = Picker.SubjectPicker()
        showPopUp(subjectPicker)
        when (val result = subjectPicker.result.await()) {
            is Picker.Result.Picked -> {
                onSubjectSelected(result.value)
                hidePopUp(subjectPicker)
            }

            is Picker.Result.Dismiss -> hidePopUp(subjectPicker)
        }
    }

    private suspend fun onTimePickerClick() {
        val timePicker = Picker.TimePicker()
        showPopUp(timePicker)
        when (val result = timePicker.result.await()) {
            is Picker.Result.Picked -> {
                onTimeSelected(result.value)
                hidePopUp(timePicker)
            }

            is Picker.Result.Dismiss -> hidePopUp(timePicker)
        }
    }

    private suspend fun onDatePickerClick() {
        val datePicker = Picker.DatePicker()
        showPopUp(datePicker)
        when (val result = datePicker.result.await()) {
            is Picker.Result.Picked -> {
                onDateSelected(result.value)
                hidePopUp(datePicker)
            }

            is Picker.Result.Dismiss -> hidePopUp(datePicker)
        }
    }

    private suspend fun onSaveHomeworkButtonClick(
        context: Context
    ) {
        // Show confirmation dialog
        val confirmationDialog = if (homeworkId == -1) {
            AlertDialog.Confirmation(
                title = UIText.StringResource(R.string.save_homework),
                message = UIText.StringResource(R.string.are_you_sure_you_want_to_save_this_homework),
            )
        } else {
            AlertDialog.Confirmation(
                title = UIText.StringResource(R.string.edit_homework),
                message = UIText.StringResource(R.string.are_you_sure_you_want_to_edit_this_homework),
            )
        }
        showPopUp(confirmationDialog)
        when (confirmationDialog.result.await()) {
            AlertDialog.Result.Positive -> {
                hidePopUp(confirmationDialog)
                onSaveHomeworkConfirmed(context)
            }

            AlertDialog.Result.Negative -> hidePopUp(confirmationDialog)
            AlertDialog.Result.Dismiss -> hidePopUp(confirmationDialog)
        }
    }

    private suspend fun onSaveHomeworkConfirmed(
        context: Context
    ) {
        val loadingPopup = AlertDialog.Loading(UIText.StringResource(R.string.saving))
        try {
            showPopUp(loadingPopup)
            saveHomework(context)
            hidePopUp(loadingPopup)

            // Show success popup
            val successPopup = AlertDialog.Information(
                message = UIText.StringResource(R.string.homework_saved)
            )
            showPopUp(successPopup)
            successPopup.result.invokeOnCompletion { hidePopUp(successPopup) }
        } catch (e: MissingRequiredFieldException) {
            hidePopUp(loadingPopup)
            val errorMessage = when (e) {
                is MissingRequiredFieldException.Title -> UIText.StringResource(R.string.homework_title_is_required)
                is MissingRequiredFieldException.Date -> UIText.StringResource(R.string.due_date_is_required)
                is MissingRequiredFieldException.Subject -> UIText.StringResource(R.string.subject_is_required)
            }
            val errorPopup = AlertDialog.Error(errorMessage)
            showPopUp(errorPopup)
            errorPopup.result.invokeOnCompletion { hidePopUp(errorPopup) }
        }
    }

    private suspend fun saveHomework(
        context: Context
    ) {
        val homework = Homework(
            id = if (homeworkId == -1) 0 else homeworkId,
            title = _state.value.homeworkTitle.ifBlank { throw MissingRequiredFieldException.Title() },
            dueDate = _state.value.date ?: throw MissingRequiredFieldException.Date(),
            subjectId = _state.value.subject?.id ?: throw MissingRequiredFieldException.Subject(),
            reminder = _state.value.time,
            description = _state.value.description,
            attachments = _state.value.attachments,
            completed = _state.value.isCompleted
        )
        val newHomeworkId = if (homeworkId == -1) {
            eventRepository.saveHomework(homework)
        } else {
            eventRepository.updateHomework(homework)
            homeworkId
        }
        if (homework.reminder != null) {
            scheduleReminder(
                context = context,
                localDateTime = LocalDateTime.of(
                    LocalDate.ofInstant(homework.dueDate.toInstant(), ZoneId.systemDefault()),
                    LocalTime.of(homework.reminder!!.hour, homework.reminder!!.minute)
                ),
                title = homework.title,
                reminderId = newHomeworkId.toInt()
            )
        }
    }

    private fun showPopUp(popUp: PopUp) {
        _state.update {
            it.copy(popUps = it.popUps + popUp)
        }
    }

    private fun hidePopUp(popUp: PopUp) {
        _state.update {
            it.copy(popUps = it.popUps - popUp)
        }
    }

    private fun onHomeworkTitleChanged(title: String) {
        _state.update {
            it.copy(homeworkTitle = title)
        }
    }

    private fun onDateSelected(date: Date) {
        _state.update {
            it.copy(date = date)
        }
    }

    private fun onTimeSelected(time: Time) {
        _state.update {
            it.copy(time = time)
        }
    }

    private fun onSubjectSelected(subject: Subject) {
        _state.update {
            it.copy(subject = subject)
        }
    }

    private fun onAttachmentsConfirmed(attachments: List<Attachment>) {
        _state.update {
            it.copy(attachments = attachments)
        }
    }

    init {
        if (homeworkId != -1) {
            viewModelScope.launch {
                eventRepository.getHomeworkById(homeworkId).collect { homeworkDto ->
                    if (homeworkDto == null) return@collect
                    _state.update {
                        it.copy(
                            isEditMode = true,
                            homeworkTitle = homeworkDto.homework.title,
                            date = homeworkDto.homework.dueDate,
                            time = homeworkDto.homework.reminder,
                            subject = homeworkDto.subject,
                            attachments = homeworkDto.homework.attachments,
                            isCompleted = homeworkDto.homework.completed,
                        )
                    }
                }
            }
        }
        getAllSubjectJob = viewModelScope.launch {
            subjectRepository.getAllSubject().collect { subjects ->
                _state.update {
                    it.copy(subjects = subjects)
                }
            }
        }
    }

    override fun onCleared() {
        getAllSubjectJob?.cancel()
        getAllSubjectJob = null
        super.onCleared()
    }

}