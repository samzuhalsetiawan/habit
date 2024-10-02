package com.wahyusembiring.grades

import com.wahyusembiring.data.model.entity.Exam
import com.wahyusembiring.data.model.entity.Homework

sealed class GradesScreenUIEvent {
    data object OnHamburgerMenuClick : GradesScreenUIEvent()
    data class OnExamClick(val exam: Exam) : GradesScreenUIEvent()
    data class OnHomeworkClick(val homework: Homework) : GradesScreenUIEvent()
}