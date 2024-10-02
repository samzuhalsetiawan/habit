package com.wahyusembiring.subject

import androidx.compose.ui.graphics.Color
import com.wahyusembiring.data.model.entity.Lecture
import com.wahyusembiring.ui.component.popup.PopUp
import com.wahyusembiring.ui.theme.primaryLight

data class CreateSubjectScreenUIState(
    val name: String = "",
    val color: Color = primaryLight,
    val room: String = "",
    val description: String = "",
    val lecture: Lecture? = null,
    val popUps: List<PopUp> = emptyList()
)
