package com.wahyusembiring.subject

import androidx.compose.ui.graphics.Color

sealed class CreateSubjectScreenUIEvent {
    data object OnSaveButtonClicked : CreateSubjectScreenUIEvent()
    data class OnSubjectNameChanged(val name: String) : CreateSubjectScreenUIEvent()
    data class OnRoomChanged(val room: String) : CreateSubjectScreenUIEvent()
    data object OnPickColorButtonClicked : CreateSubjectScreenUIEvent()
    data object OnNavigateBack : CreateSubjectScreenUIEvent()
}
