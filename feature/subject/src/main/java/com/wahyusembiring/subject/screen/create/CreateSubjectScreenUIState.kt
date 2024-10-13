package com.wahyusembiring.subject.screen.create

import androidx.compose.ui.graphics.Color
import com.wahyusembiring.data.model.entity.Lecturer
import com.wahyusembiring.ui.component.popup.PopUp
import com.wahyusembiring.ui.theme.primaryLight

data class CreateSubjectScreenUIState(
    val name: String = "",
    val color: Color = primaryLight,
    val room: String = "",
    val description: String = "",
    val lecture: Lecturer? = null,
    val lectures: List<Lecturer> = emptyList(),
    val popUps: List<PopUp> = emptyList()
)
