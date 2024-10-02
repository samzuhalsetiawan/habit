package com.wahyusembiring.reminder

import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Time
import com.wahyusembiring.ui.component.popup.PopUp
import com.wahyusembiring.ui.theme.primaryLight
import java.util.Date

data class CreateReminderScreenUIState(
    val isEditMode: Boolean = false,
    val title: String = "",
    val date: Date? = null,
    val time: Time? = null,
    val color: Color = primaryLight,
    val isCompleted: Boolean = false,
    val attachments: List<Attachment> = emptyList(),
    val description: String = "",
    val popUps: List<PopUp> = emptyList(),
)