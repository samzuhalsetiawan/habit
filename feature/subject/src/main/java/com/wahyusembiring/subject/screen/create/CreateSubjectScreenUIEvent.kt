package com.wahyusembiring.subject.screen.create

import androidx.navigation.NavController
import com.wahyusembiring.data.model.entity.Lecture

sealed class CreateSubjectScreenUIEvent {
    data object OnSaveButtonClicked : CreateSubjectScreenUIEvent()
    data class OnSubjectNameChanged(val name: String) : CreateSubjectScreenUIEvent()
    data class OnRoomChanged(val room: String) : CreateSubjectScreenUIEvent()
    data object OnPickColorButtonClicked : CreateSubjectScreenUIEvent()
    data object OnNavigateBack : CreateSubjectScreenUIEvent()
    data class OnLecturerSelected(val lecturer: Lecture) : CreateSubjectScreenUIEvent()
    data class OnAddLecturerButtonClicked(val navController: NavController) :
        CreateSubjectScreenUIEvent()
}
