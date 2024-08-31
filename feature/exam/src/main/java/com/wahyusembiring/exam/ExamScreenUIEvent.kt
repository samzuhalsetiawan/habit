package com.wahyusembiring.exam

import com.wahyusembiring.common.navigation.Screen

sealed class ExamScreenUIEvent {
    data class OnExamNameChanged(val name: String) : ExamScreenUIEvent()
    data object OnExamDatePickerClick : ExamScreenUIEvent()
    data object OnExamTimePickerClick : ExamScreenUIEvent()
    data object OnExamSubjectPickerClick : ExamScreenUIEvent()
    data object OnExamCategoryPickerClick : ExamScreenUIEvent()
    data object OnExamAttachmentPickerClick : ExamScreenUIEvent()
    data object OnSaveExamButtonClick : ExamScreenUIEvent()
    data object OnNavigateBack : ExamScreenUIEvent()
    data class OnNavigate(val screen: Screen) : ExamScreenUIEvent()
}