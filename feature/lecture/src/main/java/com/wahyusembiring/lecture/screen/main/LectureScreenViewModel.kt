package com.wahyusembiring.lecture.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.wahyusembiring.common.navigation.Screen
import com.wahyusembiring.data.repository.LectureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LectureScreenViewModel @Inject constructor(
    private val lectureRepository: LectureRepository
) : ViewModel() {


    init {
        viewModelScope.launch {
            lectureRepository.getAllLectureWithSubjects().collect {
                _state.update {
                    it.copy(listOfLectureWithSubjects = it.listOfLectureWithSubjects)
                }
            }
        }
    }


    private fun onHamburgerMenuClick() {
        _state.update {
            it.copy(showDrawer = true)
        }
    }

    private val _state = MutableStateFlow(LectureScreenUIState())
    val state = _state.asStateFlow()

    fun onUIEvent(event: LectureScreenUIEvent) {
        when (event) {
            is LectureScreenUIEvent.OnHamburgerMenuClick -> {
                onHamburgerMenuClick()
            }

            is LectureScreenUIEvent.OnAddLectureClick -> onAddLectureClick(event.navController)
        }
    }

    private fun onAddLectureClick(navController: NavController) {
        navController.navigate(Screen.AddLecture)
    }

}