package com.wahyusembiring.lecture.screen.main

import androidx.navigation.NavController
import com.wahyusembiring.data.model.LecturerWithSubject

data class LectureScreenUIState(
    val listOfLecturerWithSubjects: List<LecturerWithSubject> = emptyList()
)

sealed class LectureScreenUIEvent {
    data class OnAddLectureClick(val navController: NavController) : LectureScreenUIEvent()
}