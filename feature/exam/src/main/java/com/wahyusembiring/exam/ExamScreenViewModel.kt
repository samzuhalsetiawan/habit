package com.wahyusembiring.exam

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.common.util.launch
import com.wahyusembiring.common.util.scheduleReminder
import com.wahyusembiring.data.model.entity.Exam
import com.wahyusembiring.data.repository.EventRepository
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

@HiltViewModel(assistedFactory = ExamScreenViewModel.Factory::class)
class ExamScreenViewModel @AssistedInject constructor(
    @Assisted val examId: Int = -1,
    private val eventRepository: EventRepository,
    private val subjectRepository: SubjectRepository
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(examId: Int = -1): ExamScreenViewModel
    }

    private val _state = MutableStateFlow(ExamScreenUIState())
    val state = _state.asStateFlow()

    private var getAllSubjectJob: Job? = null


    fun onUIEvent(event: ExamScreenUIEvent) {
        when (event) {
            is ExamScreenUIEvent.OnExamNameChanged -> onExamNameChanged(event.name)
            is ExamScreenUIEvent.OnExamDatePickerClick -> launch { onExamDatePickerClick() }
            is ExamScreenUIEvent.OnExamTimePickerClick -> launch { onExamTimePickerClick() }
            is ExamScreenUIEvent.OnExamSubjectPickerClick -> launch { onExamSubjectPickerClick() }
            is ExamScreenUIEvent.OnExamAttachmentPickerClick -> launch { onExamAttachmentPickerClick() }
            is ExamScreenUIEvent.OnExamCategoryPickerClick -> launch { onExamCategoryPickerClick() }
            is ExamScreenUIEvent.OnSaveExamButtonClick -> launch { onSaveExamButtonClick(event.context) }
            else -> Unit
        }
    }

    init {
        if (examId != -1) {
            viewModelScope.launch {
                eventRepository.getExamById(examId).collect { examDto ->
                    if (examDto == null) return@collect
                    _state.update {
                        it.copy(
                            isEditMode = true,
                            name = examDto.exam.title,
                            date = examDto.exam.date,
                            time = examDto.exam.reminder,
                            subject = examDto.subject,
                            category = examDto.exam.category,
                            score = examDto.exam.score,
                            attachments = examDto.exam.attachments
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

    private suspend fun onSaveExamButtonClick(
        context: Context
    ) {
        val confirmationDalog = if (examId == -1) {
            AlertDialog.Confirmation(
                title = UIText.StringResource(R.string.save_exam),
                message = UIText.StringResource(R.string.are_you_sure_you_want_to_save_this_exam),
            )
        } else {
            AlertDialog.Confirmation(
                title = UIText.StringResource(R.string.edit_exam),
                message = UIText.StringResource(R.string.are_you_sure_you_want_to_edit_this_exam),
            )
        }
        showPopUp(confirmationDalog)
        when (confirmationDalog.result.await()) {
            AlertDialog.Result.Positive -> {
                hidePopUp(confirmationDalog)
                saveExam(context)
            }

            AlertDialog.Result.Negative -> hidePopUp(confirmationDalog)
            AlertDialog.Result.Dismiss -> hidePopUp(confirmationDalog)
        }
    }

    private suspend fun saveExam(
        context: Context
    ) {
        val loading =
            AlertDialog.Loading(UIText.StringResource(com.wahyusembiring.ui.R.string.saving))
        showPopUp(loading)
        try {
            val exam = Exam(
                id = if (examId != -1) examId else 0,
                title = _state.value.name,
                date = _state.value.date ?: throw MissingRequiredFieldException.Date(),
                reminder = _state.value.time ?: throw MissingRequiredFieldException.Time(),
                subjectId = _state.value.subject?.id
                    ?: throw MissingRequiredFieldException.Subject(),
                category = _state.value.category,
                description = "",
                attachments = _state.value.attachments,
                score = _state.value.score
            )
            val newExamId = if (examId == -1) {
                eventRepository.saveExam(exam)
            } else {
                eventRepository.updateExam(exam)
                examId
            }
            scheduleReminder(
                context = context,
                localDateTime = LocalDateTime.of(
                    LocalDate.ofInstant(exam.date.toInstant(), ZoneId.systemDefault()),
                    LocalTime.of(exam.reminder!!.hour, exam.reminder!!.minute)
                ),
                title = exam.title,
                reminderId = newExamId.toInt()
            )
            hidePopUp(loading)

            val success = AlertDialog.Information(
                message = UIText.StringResource(R.string.exam_saved)
            )
            showPopUp(success)
            success.result.invokeOnCompletion { hidePopUp(success) }
        } catch (e: MissingRequiredFieldException) {
            hidePopUp(loading)
            val errorMessage = when (e) {
                is MissingRequiredFieldException.Date -> UIText.StringResource(R.string.date_cannot_be_empty)
                is MissingRequiredFieldException.Subject -> UIText.StringResource(R.string.subject_cannot_be_empty)
                is MissingRequiredFieldException.Time -> UIText.StringResource(R.string.time_cannot_be_empty)
                is MissingRequiredFieldException.Title -> UIText.StringResource(R.string.exam_name_cannot_be_empty)
            }
            val error = AlertDialog.Error(message = errorMessage)
            showPopUp(error)
            error.result.invokeOnCompletion { hidePopUp(error) }
        }
    }

    private fun onExamNameChanged(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    private suspend fun onExamDatePickerClick() {
        val datePicker = Picker.DatePicker()
        showPopUp(datePicker)
        when (val result = datePicker.result.await()) {
            is Picker.Result.Picked -> {
                hidePopUp(datePicker)
                _state.value = _state.value.copy(date = result.value)
            }

            is Picker.Result.Dismiss -> hidePopUp(datePicker)
        }
    }

    private suspend fun onExamTimePickerClick() {
        val timePicker = Picker.TimePicker()
        showPopUp(timePicker)
        when (val result = timePicker.result.await()) {
            is Picker.Result.Picked -> {
                hidePopUp(timePicker)
                _state.value = _state.value.copy(time = result.value)
            }

            is Picker.Result.Dismiss -> hidePopUp(timePicker)
        }
    }

    private suspend fun onExamSubjectPickerClick() {
        val subjectPicker = Picker.SubjectPicker()
        showPopUp(subjectPicker)
        when (val result = subjectPicker.result.await()) {
            is Picker.Result.Picked -> {
                hidePopUp(subjectPicker)
                _state.value = _state.value.copy(subject = result.value)
            }

            is Picker.Result.Dismiss -> hidePopUp(subjectPicker)
        }
    }

    private suspend fun onExamCategoryPickerClick() {
        val categoryPicker = Picker.ExamCategoryPicker()
        showPopUp(categoryPicker)
        when (val result = categoryPicker.result.await()) {
            is Picker.Result.Picked -> {
                hidePopUp(categoryPicker)
                _state.value = _state.value.copy(category = result.value)
            }

            is Picker.Result.Dismiss -> hidePopUp(categoryPicker)
        }
    }

    private suspend fun onExamAttachmentPickerClick() {
        val attachmentPicker = Picker.AttachmentPicker()
        showPopUp(attachmentPicker)
        when (val result = attachmentPicker.result.await()) {
            is Picker.Result.Picked -> {
                hidePopUp(attachmentPicker)
                _state.value = _state.value.copy(attachments = result.value)
            }

            is Picker.Result.Dismiss -> hidePopUp(attachmentPicker)
        }
    }


    private fun showPopUp(popUp: PopUp) {
        _state.value = _state.value.copy(popUps = _state.value.popUps + popUp)
    }

    private fun hidePopUp(popUp: PopUp) {
        _state.value = _state.value.copy(popUps = _state.value.popUps - popUp)
    }

    override fun onCleared() {
        getAllSubjectJob?.cancel()
        getAllSubjectJob = null
        super.onCleared()
    }

}