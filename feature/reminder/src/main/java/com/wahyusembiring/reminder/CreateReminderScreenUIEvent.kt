package com.wahyusembiring.reminder

sealed class CreateReminderScreenUIEvent {

   data class OnTitleChanged(val title: String) : CreateReminderScreenUIEvent()
   data object OnDatePickerButtonClick : CreateReminderScreenUIEvent()
   data object OnTimePickerButtonClick : CreateReminderScreenUIEvent()
   data object OnColorPickerButtonClick : CreateReminderScreenUIEvent()
   data object OnAttachmentPickerButtonClick : CreateReminderScreenUIEvent()
   data object OnSaveButtonClicked : CreateReminderScreenUIEvent()

}