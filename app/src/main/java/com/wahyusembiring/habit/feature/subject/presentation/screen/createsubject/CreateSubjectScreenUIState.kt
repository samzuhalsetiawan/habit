package com.wahyusembiring.habit.feature.subject.presentation.screen.createsubject

import androidx.compose.ui.graphics.Color
import com.wahyusembiring.habit.feature.lecture.domain.model.Lecture

data class CreateSubjectScreenUIState(
   val name: String = "",
   val color: Color = Color.Yellow,
   val room: String = "",
   val description: String = "",
   val lecture: Lecture? = null,
   val popUp: CreateSubjectScreenPopUp? = null
)

sealed class CreateSubjectScreenPopUp {
   data object SaveSubjectLoading : CreateSubjectScreenPopUp()
   data object ColorPicker : CreateSubjectScreenPopUp()
   data object SaveSubjectSuccess : CreateSubjectScreenPopUp()
   data object NameIsRequired : CreateSubjectScreenPopUp()
   data object RoomIsRequired : CreateSubjectScreenPopUp()
   data object SaveSubjectConfirmation : CreateSubjectScreenPopUp()
}
