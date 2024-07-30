package com.wahyusembiring.habit.feature.subject.presentation.screen.createsubject

import androidx.compose.ui.graphics.Color

sealed class CreateSubjectScreenUIEvent {
   data object OnSaveButtonClicked : CreateSubjectScreenUIEvent()
   data class OnSubjectNameChanged(val name: String) : CreateSubjectScreenUIEvent()
   data class OnRoomChanged(val room: String) : CreateSubjectScreenUIEvent()
   data object OnPickColorButtonClicked : CreateSubjectScreenUIEvent()
   data class OnColorConfirmed(val color: Color) : CreateSubjectScreenUIEvent()
   data class HidePopUp(val popUp: CreateSubjectScreenPopUp) : CreateSubjectScreenUIEvent()
}
