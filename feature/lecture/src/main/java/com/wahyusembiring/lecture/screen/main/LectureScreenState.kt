package com.wahyusembiring.lecture.screen.main

import androidx.navigation.NavController
import com.wahyusembiring.data.model.LecturerWithSubject

data class LectureScreenUIState(
    val showDrawer: Boolean = false,
    val listOfLecturerWithSubjects: List<LecturerWithSubject> = emptyList()
)

sealed class LectureScreenUIEvent {
    data object OnHamburgerMenuClick : LectureScreenUIEvent()
    data class OnAddLectureClick(val navController: NavController) : LectureScreenUIEvent()
}