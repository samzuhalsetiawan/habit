package com.wahyusembiring.homework

sealed class CreateHomeworkUIEvent {
    data class OnHomeworkTitleChanged(val title: String) : CreateHomeworkUIEvent()
    data object OnSaveHomeworkButtonClicked : CreateHomeworkUIEvent()
    data object OnPickDateButtonClicked : CreateHomeworkUIEvent()
    data object OnPickTimeButtonClicked : CreateHomeworkUIEvent()
    data object OnPickSubjectButtonClicked : CreateHomeworkUIEvent()
    data object OnPickAttachmentButtonClicked : CreateHomeworkUIEvent()
    data object OnNavigateBack : CreateHomeworkUIEvent()
    data object OnNavigateToCreateSubjectScreen : CreateHomeworkUIEvent()
}