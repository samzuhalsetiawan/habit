package com.wahyusembiring.exam

import android.content.Context
import com.wahyusembiring.common.navigation.Screen

sealed class ExamScreenUIEvent {
    data class OnExamNameChanged(val name: String) : ExamScreenUIEvent()
    data object OnExamDatePickerClick : ExamScreenUIEvent()
    data object OnExamTimePickerClick : ExamScreenUIEvent()
    data object OnExamSubjectPickerClick : ExamScreenUIEvent()
    data object OnExamCategoryPickerClick : ExamScreenUIEvent()
    data object OnExamAttachmentPickerClick : ExamScreenUIEvent()
    data class OnSaveExamButtonClick(val context: Context) : ExamScreenUIEvent()
    data object OnNavigateBack : ExamScreenUIEvent()
    data class OnNavigate(val screen: Screen) : ExamScreenUIEvent()
}