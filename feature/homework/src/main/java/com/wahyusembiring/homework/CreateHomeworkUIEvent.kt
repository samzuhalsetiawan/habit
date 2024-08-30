package com.wahyusembiring.homework

import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.data.model.Time
import com.wahyusembiring.ui.component.popup.PopUp
import java.util.Date

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