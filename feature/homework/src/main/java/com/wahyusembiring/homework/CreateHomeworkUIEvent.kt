package com.wahyusembiring.homework

import android.content.Context

sealed class CreateHomeworkUIEvent {
    data class OnHomeworkTitleChanged(val title: String) : CreateHomeworkUIEvent()
    data class OnSaveHomeworkButtonClicked(val context: Context) : CreateHomeworkUIEvent()
    data object OnPickDateButtonClicked : CreateHomeworkUIEvent()
    data object OnPickTimeButtonClicked : CreateHomeworkUIEvent()
    data object OnPickSubjectButtonClicked : CreateHomeworkUIEvent()
    data object OnPickAttachmentButtonClicked : CreateHomeworkUIEvent()
    data object OnNavigateBack : CreateHomeworkUIEvent()
    data object OnNavigateToCreateSubjectScreen : CreateHomeworkUIEvent()
}