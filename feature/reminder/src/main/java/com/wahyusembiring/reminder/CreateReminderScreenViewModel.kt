package com.wahyusembiring.reminder

import androidx.lifecycle.ViewModel
import com.wahyusembiring.common.util.launch
import com.wahyusembiring.data.model.Reminder
import com.wahyusembiring.data.repository.ReminderRepository
import com.wahyusembiring.ui.component.popup.AlertDialog
import com.wahyusembiring.ui.component.popup.Picker
import com.wahyusembiring.ui.component.popup.PopUp
import com.wahyusembiring.ui.util.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateReminderScreenViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateReminderScreenUIState())
    val state = _state.asStateFlow()

    fun onUIEvent(event: CreateReminderScreenUIEvent) {
        when (event) {
            is CreateReminderScreenUIEvent.OnTitleChanged -> onTitleChanged(event.title)
            is CreateReminderScreenUIEvent.OnDatePickerButtonClick -> launch { onDatePickerButtonClick() }
            is CreateReminderScreenUIEvent.OnTimePickerButtonClick -> launch { onTimePickerButtonClick() }
            is CreateReminderScreenUIEvent.OnColorPickerButtonClick -> launch { onColorPickerButtonClick() }
            is CreateReminderScreenUIEvent.OnAttachmentPickerButtonClick -> launch { onAttachmentPickerButtonClick() }
            is CreateReminderScreenUIEvent.OnSaveButtonClicked -> launch { onSaveButtonClicked() }
            else -> Unit
        }
    }

    private suspend fun onSaveButtonClicked() {
        val confirmationDialog = AlertDialog.Confirmation(
            title = UIText.StringResource(R.string.save_reminder),
            message = UIText.StringResource(R.string.are_you_sure_you_want_to_save_this_reminder)
        )
        showPopUp(confirmationDialog)
        when (confirmationDialog.result.await()) {
            AlertDialog.Result.Positive -> {
                hidePopUp(confirmationDialog)
                onSaveReminderConfirmed()
            }

            AlertDialog.Result.Negative -> hidePopUp(confirmationDialog)
            AlertDialog.Result.Dismiss -> hidePopUp(confirmationDialog)
        }
    }

    private suspend fun onSaveReminderConfirmed() {
        val loadingDialog = AlertDialog.Loading(UIText.StringResource(R.string.saving_reminder))
        try {
            showPopUp(loadingDialog)
            saveReminder()
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

    private suspend fun saveReminder() {
        val reminder = Reminder(
            title = _state.value.title.ifBlank { throw MissingRequiredFieldException.Title() },
            date = _state.value.date ?: throw MissingRequiredFieldException.Date(),
            time = _state.value.time ?: throw MissingRequiredFieldException.Date(),
            color = _state.value.color,
            description = _state.value.description
        )
        reminderRepository.saveReminderWithAttachments(
            reminder = reminder,
            attachments = _state.value.attachments
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