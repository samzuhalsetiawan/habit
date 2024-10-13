package com.wahyusembiring.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.common.NOTIFICATION_ID_EXTRA
import com.wahyusembiring.common.NOTIFICATION_TITLE_EXTRA
import com.wahyusembiring.common.NotificationBroadcastReceiver
import com.wahyusembiring.common.util.launch
import com.wahyusembiring.common.util.scheduleReminder
import com.wahyusembiring.data.model.entity.Reminder
import com.wahyusembiring.data.repository.EventRepository
import com.wahyusembiring.data.repository.ReminderRepository
import com.wahyusembiring.ui.component.popup.AlertDialog
import com.wahyusembiring.ui.component.popup.Picker
import com.wahyusembiring.ui.component.popup.PopUp
import com.wahyusembiring.ui.util.UIText
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel(assistedFactory = CreateReminderScreenViewModel.Factory::class)
class CreateReminderScreenViewModel @AssistedInject constructor(
    @Assisted val reminderId: Int = -1,
    private val eventRepository: EventRepository
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(reminderId: Int = -1): CreateReminderScreenViewModel
    }

    private val _state = MutableStateFlow(CreateReminderScreenUIState())
    val state = _state.asStateFlow()


    init {
        if (reminderId != -1) {
            viewModelScope.launch {
                eventRepository.getReminderById(reminderId).collect { reminderDto ->
                    if (reminderDto == null) return@collect
                    _state.update {
                        it.copy(
                            isEditMode = true,
                            title = reminderDto.title,
                            date = reminderDto.date,
                            time = reminderDto.time,
                            color = reminderDto.color,
                            attachments = reminderDto.attachments,
                            description = reminderDto.description,
                            isCompleted = reminderDto.completed
                        )
                    }
                }
            }
        }
    }

    fun onUIEvent(event: CreateReminderScreenUIEvent) {
        when (event) {
            is CreateReminderScreenUIEvent.OnTitleChanged -> onTitleChanged(event.title)
            is CreateReminderScreenUIEvent.OnDatePickerButtonClick -> launch { onDatePickerButtonClick() }
            is CreateReminderScreenUIEvent.OnTimePickerButtonClick -> launch { onTimePickerButtonClick() }
            is CreateReminderScreenUIEvent.OnColorPickerButtonClick -> launch { onColorPickerButtonClick() }
            is CreateReminderScreenUIEvent.OnAttachmentPickerButtonClick -> launch { onAttachmentPickerButtonClick() }
            is CreateReminderScreenUIEvent.OnSaveButtonClicked -> launch { onSaveButtonClicked(event.context) }
            else -> Unit
        }
    }

    private suspend fun onSaveButtonClicked(
        context: Context
    ) {
        val confirmationDialog = if (reminderId == -1) {
            AlertDialog.Confirmation(
                title = UIText.StringResource(R.string.save_reminder),
                message = UIText.StringResource(R.string.are_you_sure_you_want_to_save_this_reminder)
            )
        } else {
            AlertDialog.Confirmation(
                title = UIText.StringResource(R.string.edit_reminder),
                message = UIText.StringResource(R.string.are_you_sure_you_want_to_edit_this_reminder)
            )
        }
        showPopUp(confirmationDialog)
        when (confirmationDialog.result.await()) {
            AlertDialog.Result.Positive -> {
                hidePopUp(confirmationDialog)
                onSaveReminderConfirmed(context)
            }

            AlertDialog.Result.Negative -> hidePopUp(confirmationDialog)
            AlertDialog.Result.Dismiss -> hidePopUp(confirmationDialog)
        }
    }

    private suspend fun onSaveReminderConfirmed(
        context: Context
    ) {
        val loadingDialog = AlertDialog.Loading(UIText.StringResource(R.string.saving_reminder))
        try {
            showPopUp(loadingDialog)
            saveReminder(context)
            hidePopUp(loadingDialog)

            val successDialog = AlertDialog.Information(
                message = UIText.StringResource(R.string.reminder_saved),
            )
            showPopUp(successDialog)
            successDialog.result.invokeOnCompletion { hidePopUp(successDialog) }
        } catch (e: MissingRequiredFieldException) {
            hidePopUp(loadingDialog)
            val errorMessage = when (e) {
                is MissingRequiredFieldException.Title -> UIText.StringResource(R.string.title_is_required)
                is MissingRequiredFieldException.Date -> UIText.StringResource(R.string.date_is_required)
                is MissingRequiredFieldException.Time -> UIText.StringResource(R.string.time_is_required)
            }
            val errorDialog = AlertDialog.Error(message = errorMessage)
            showPopUp(errorDialog)
            errorDialog.result.invokeOnCompletion { hidePopUp(errorDialog) }
        }
    }

    private suspend fun saveReminder(
        context: Context
    ) {
        val reminder = Reminder(
            id = if (reminderId != -1) reminderId else 0,
            title = _state.value.title.ifBlank { throw MissingRequiredFieldException.Title() },
            date = _state.value.date ?: throw MissingRequiredFieldException.Date(),
            time = _state.value.time ?: throw MissingRequiredFieldException.Date(),
            color = _state.value.color,
            attachments = _state.value.attachments,
            description = _state.value.description,
            completed = _state.value.isCompleted
        )
        val savedReminderId = if (reminderId == -1) {
            eventRepository.saveReminder(reminder)
        } else {
            eventRepository.updateReminder(reminder)
            reminderId
        }
        scheduleReminder(
            context = context,
            localDateTime = LocalDateTime.of(
                LocalDate.ofInstant(reminder.date.toInstant(), ZoneId.systemDefault()),
                LocalTime.of(reminder.time.hour, reminder.time.minute)
            ),
            title = reminder.title,
            reminderId = savedReminderId.toInt()
        )
    }


    private fun onTitleChanged(title: String) {
        _state.update {
            it.copy(title = title)
        }
    }

    private suspend fun onDatePickerButtonClick() {
        val datePicker = Picker.DatePicker()
        showPopUp(datePicker)
        when (val result = datePicker.result.await()) {
            is Picker.Result.Picked -> {
                hidePopUp(datePicker)
                _state.update {
                    it.copy(date = result.value)
                }
            }

            is Picker.Result.Dismiss -> hidePopUp(datePicker)
        }
    }

    private suspend fun onTimePickerButtonClick() {
        val timePicker = Picker.TimePicker()
        showPopUp(timePicker)
        when (val result = timePicker.result.await()) {
            is Picker.Result.Picked -> {
                hidePopUp(timePicker)
                _state.update {
                    it.copy(time = result.value)
                }
            }

            is Picker.Result.Dismiss -> hidePopUp(timePicker)
        }
    }

    private suspend fun onColorPickerButtonClick() {
        val colorPicker = Picker.ColorPicker()
        showPopUp(colorPicker)
        when (val result = colorPicker.result.await()) {
            is Picker.Result.Picked -> {
                hidePopUp(colorPicker)
                _state.update {
                    it.copy(color = result.value)
                }
            }

            is Picker.Result.Dismiss -> hidePopUp(colorPicker)
        }
    }

    private suspend fun onAttachmentPickerButtonClick() {
        val attachmentPicker = Picker.AttachmentPicker()
        showPopUp(attachmentPicker)
        when (val result = attachmentPicker.result.await()) {
            is Picker.Result.Picked -> {
                hidePopUp(attachmentPicker)
                _state.update {
                    it.copy(attachments = it.attachments + result.value)
                }
            }

            is Picker.Result.Dismiss -> hidePopUp(attachmentPicker)
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

}