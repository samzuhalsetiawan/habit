package com.wahyusembiring.exam

import android.net.Uri
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.entity.ExamCategory
import com.wahyusembiring.data.model.entity.Subject
import com.wahyusembiring.data.model.Time
import com.wahyusembiring.ui.component.popup.PopUp
import java.util.Date

data class ExamScreenUIState(
    val isEditMode: Boolean = false,
    val name: String = "",
    val date: Date? = null,
    val time: Time? = null,
    val subjects: List<Subject> = emptyList(),
    val subject: Subject? = null,
    val score: Int? = null,
    val category: ExamCategory = ExamCategory.WRITTEN,
    val attachments: List<Attachment> = emptyList(),
    val popUps: List<PopUp> = emptyList(),
)