package com.wahyusembiring.habit.feature.homework.presentation.screen.createhomework

import com.wahyusembiring.habit.core.domain.model.Attachment
import com.wahyusembiring.habit.core.domain.model.Time
import com.wahyusembiring.habit.feature.subject.domain.model.Subject
import java.util.Date

data class CreateHomeworkScreenUIState(
   val homeworkTitle: String = "",
   val date: Date? = null,
   val time: Time? = null,
   val subjects: List<Subject> = emptyList(),
   val subject: Subject? = null,
   val attachments: List<Attachment> = emptyList(),
   val description: String = "",
   val popUp: CreateHomeworkScreenPopUp? = null
)

sealed class CreateHomeworkScreenPopUp {
   data object DatePicker : CreateHomeworkScreenPopUp()
   data object TimePicker : CreateHomeworkScreenPopUp()
   data object SubjectPicker : CreateHomeworkScreenPopUp()
   data object AttachmentPicker : CreateHomeworkScreenPopUp()
   data object SaveHomeworkLoading : CreateHomeworkScreenPopUp()
   data object SaveHomeworkConfirmation : CreateHomeworkScreenPopUp()
   data object SaveHomeworkSuccess : CreateHomeworkScreenPopUp()
   data object HomeworkTitleIsRequired : CreateHomeworkScreenPopUp()
   data object DateIsRequired : CreateHomeworkScreenPopUp()
   data object SubjectIsRequired : CreateHomeworkScreenPopUp()
}