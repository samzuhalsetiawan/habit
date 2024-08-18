package com.wahyusembiring.homework

import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.data.model.Time
import com.wahyusembiring.ui.component.popup.AlertDialog
import com.wahyusembiring.ui.component.popup.PopUp
import java.util.Date

data class CreateHomeworkScreenUIState(
   val homeworkTitle: String = "",
   val date: Date? = null,
   val time: Time? = null,
   val subjects: List<Subject> = emptyList(),
   val subject: Subject? = null,
   val attachments: List<Attachment> = emptyList(),
   val description: String = "",
   val popUps: List<PopUp> = emptyList(),
)