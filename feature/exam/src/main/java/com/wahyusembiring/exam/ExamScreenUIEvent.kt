package com.wahyusembiring.exam

sealed class ExamScreenUIEvent {
    data class OnExamNameChanged(val name: String) : ExamScreenUIEvent()
    data object OnExamDatePickerClick : ExamScreenUIEvent()
    data object OnExamTimePickerClick : ExamScreenUIEvent()
    data object OnExamSubjectPickerClick : ExamScreenUIEvent()
    data object OnExamCategoryPickerClick : ExamScreenUIEvent()
    data object OnExamAttachmentPickerClick : ExamScreenUIEvent()
    data object OnSaveExamButtonClick : ExamScreenUIEvent()
    data object OnNavigateBack : ExamScreenUIEvent()
}