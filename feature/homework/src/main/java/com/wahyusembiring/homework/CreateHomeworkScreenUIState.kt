package com.wahyusembiring.homework

import android.net.Uri
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.entity.Subject
import com.wahyusembiring.data.model.Time
import com.wahyusembiring.ui.component.popup.PopUp
import java.util.Date

data class CreateHomeworkScreenUIState(
    val isEditMode: Boolean = false,
    val homeworkTitle: String = "",
    val date: Date? = null,
    val time: Time? = null,
    val subjects: List<Subject> = emptyList(),
    val subject: Subject? = null,
    val attachments: List<Attachment> = emptyList(),
    val isCompleted: Boolean = false,
    val description: String = "",
    val popUps: List<PopUp> = emptyList(),
)