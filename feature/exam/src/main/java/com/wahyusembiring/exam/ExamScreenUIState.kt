package com.wahyusembiring.exam

import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.ExamCategory
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.data.model.Time
import com.wahyusembiring.ui.component.popup.PopUp
import java.util.Date

data class ExamScreenUIState(
   val name: String = "",
   val date: Date? = null,
   val time: Time? = null,
   val subject: Subject? = null,
   val category: ExamCategory = ExamCategory.WRITTEN,
   val attachments: List<Attachment> = emptyList(),
   val popUps: List<PopUp> = emptyList(),
)