package com.wahyusembiring.habit.feature.homework.presentation.screen.createhomework

import com.wahyusembiring.habit.core.domain.model.Attachment
import com.wahyusembiring.habit.core.domain.model.Time
import com.wahyusembiring.habit.feature.subject.domain.model.Subject
import java.util.Date

sealed class CreateHomeworkUIEvent {
   data class OnHomeworkTitleChanged(val title: String) : CreateHomeworkUIEvent()
   data class OnDateSelected(val date: Date) : CreateHomeworkUIEvent()
   data class OnTimeSelected(val time: Time) : CreateHomeworkUIEvent()
   data class OnSubjectSelected(val subject: Subject) : CreateHomeworkUIEvent()
   data class OnAttachmentsConfirmed(val attachments: List<Attachment>) : CreateHomeworkUIEvent()
   data object OnSaveHomeworkButtonClicked : CreateHomeworkUIEvent()
   data object OnSaveHomeworkConfirmed : CreateHomeworkUIEvent()
   data object OnPickDateButtonClicked : CreateHomeworkUIEvent()
   data object OnPickTimeButtonClicked : CreateHomeworkUIEvent()
   data object OnPickSubjectButtonClicked : CreateHomeworkUIEvent()
   data object OnPickAttachmentButtonClicked : CreateHomeworkUIEvent()
   data class HidePopUp(val popUp: CreateHomeworkScreenPopUp) : CreateHomeworkUIEvent()
}