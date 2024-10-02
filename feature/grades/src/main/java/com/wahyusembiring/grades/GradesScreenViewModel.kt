package com.wahyusembiring.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.common.util.launch
import com.wahyusembiring.data.model.entity.Exam
import com.wahyusembiring.data.model.entity.Homework
import com.wahyusembiring.data.repository.EventRepository
import com.wahyusembiring.data.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GradesScreenViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            subjectRepository.getAllSubjectWithExamAndHomework()
                .collect { subjectWithExamAndHomework ->
                    println("Broooooo ${subjectWithExamAndHomework.size}")
                    _uiState.update {
                        it.copy(
                            subjects = subjectWithExamAndHomework
                        )
                    }
                }
        }
    }

    private val _uiState = MutableStateFlow(GradesScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun onUIEvent(event: GradesScreenUIEvent) {

    }


}