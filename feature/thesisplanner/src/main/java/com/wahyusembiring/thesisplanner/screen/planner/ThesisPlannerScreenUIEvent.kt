package com.wahyusembiring.thesisplanner.screen.planner

import android.net.Uri
import com.wahyusembiring.data.model.File
import com.wahyusembiring.data.model.entity.Task

sealed class ThesisPlannerScreenUIEvent {
    data class OnArticleClick(val article: File) : ThesisPlannerScreenUIEvent()
    data object OnAddArticleClick : ThesisPlannerScreenUIEvent()
    class OnDocumentPickerResult(val files: List<File>) : ThesisPlannerScreenUIEvent()
    data class OnDeleteArticleClick(val article: File) : ThesisPlannerScreenUIEvent()
    data object OnBackMenuClick : ThesisPlannerScreenUIEvent()
    data class OnSaveTaskClick(val task: Task) : ThesisPlannerScreenUIEvent()
    data class OnTaskCompletedStatusChange(val task: Task, val isCompleted: Boolean) :
        ThesisPlannerScreenUIEvent()

    data class OnDeleteTaskClick(val task: Task) : ThesisPlannerScreenUIEvent()
    data class OnThesisTitleChange(val thesisName: String) : ThesisPlannerScreenUIEvent()
}