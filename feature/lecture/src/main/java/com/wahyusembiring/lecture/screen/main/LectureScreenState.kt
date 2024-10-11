package com.wahyusembiring.lecture.screen.main

import androidx.navigation.NavController
import com.wahyusembiring.data.model.LectureWithSubject

data class LectureScreenUIState(
    val showDrawer: Boolean = false,
    val listOfLectureWithSubjects: List<LectureWithSubject> = emptyList()
)

sealed class LectureScreenUIEvent {
    data object OnHamburgerMenuClick : LectureScreenUIEvent()
    data class OnAddLectureClick(val navController: NavController) : LectureScreenUIEvent()
}