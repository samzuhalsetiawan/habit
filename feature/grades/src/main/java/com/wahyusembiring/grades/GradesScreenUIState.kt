package com.wahyusembiring.grades

import com.wahyusembiring.data.model.SubjectWithExamAndHomework

data class GradesScreenUIState(
    val subjects: List<SubjectWithExamAndHomework> = emptyList()
)