package com.wahyusembiring.reminder

import android.content.Context

sealed class CreateReminderScreenUIEvent {

    data class OnTitleChanged(val title: String) : CreateReminderScreenUIEvent()
    data object OnDatePickerButtonClick : CreateReminderScreenUIEvent()
    data object OnTimePickerButtonClick : CreateReminderScreenUIEvent()
    data object OnColorPickerButtonClick : CreateReminderScreenUIEvent()
    data object OnAttachmentPickerButtonClick : CreateReminderScreenUIEvent()
    data class OnSaveButtonClicked(val context: Context) : CreateReminderScreenUIEvent()
    data object OnNavigateBack : CreateReminderScreenUIEvent()

}