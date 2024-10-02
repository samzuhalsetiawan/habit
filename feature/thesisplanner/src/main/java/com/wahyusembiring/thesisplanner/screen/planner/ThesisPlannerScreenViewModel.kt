package com.wahyusembiring.thesisplanner.screen.planner

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.common.util.launch
import com.wahyusembiring.data.model.File
import com.wahyusembiring.data.model.ThesisWithTask
import com.wahyusembiring.data.model.entity.Task
import com.wahyusembiring.data.model.entity.Thesis
import com.wahyusembiring.data.repository.ThesisRepository
import com.wahyusembiring.ui.component.popup.AlertDialog
import com.wahyusembiring.ui.component.popup.PopUp
import com.wahyusembiring.ui.util.UIText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.wahyusembiring.thesisplanner.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ThesisPlannerScreenViewModel.Factory::class)
class ThesisPlannerScreenViewModel @AssistedInject constructor(
    @Assisted private val thesisId: Int,
    private val thesisRepository: ThesisRepository
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(thesisId: Int): ThesisPlannerScreenViewModel
    }

    private val debounceDuration = 1000L
    private var savingTitleJob: Job? = null
    private lateinit var thesis: ThesisWithTask

    init {
        viewModelScope.launch {
            thesisRepository.getThesisById(thesisId).collect { thesis ->
                this@ThesisPlannerScreenViewModel.thesis = thesis
                _uiState.update {
                    it.copy(
                        thesisTitle = thesis.thesis.title,
                        editedThesisTitle = thesis.thesis.title,
                        articles = thesis.thesis.articles,
                        tasks = thesis.tasks
                    )
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(ThesisPlannerScreenUIState())
    val uiState: StateFlow<ThesisPlannerScreenUIState> = _uiState.asStateFlow()

    fun onUIEvent(event: ThesisPlannerScreenUIEvent) {
        when (event) {
            is ThesisPlannerScreenUIEvent.OnAddArticleClick -> TODO()
            is ThesisPlannerScreenUIEvent.OnArticleClick -> TODO()
            is ThesisPlannerScreenUIEvent.OnDeleteArticleClick -> launch {
                onDeleteArticleClick(
                    event.article
                )
            }

            is ThesisPlannerScreenUIEvent.OnDocumentPickerResult -> onDocumentPickerResult(event.files)
            is ThesisPlannerScreenUIEvent.OnThesisTitleChange -> onThesisTitleChange(event.thesisName)
            is ThesisPlannerScreenUIEvent.OnSaveTaskClick -> launch { onSaveTaskClick(event.task) }
            is ThesisPlannerScreenUIEvent.OnTaskCompletedStatusChange -> launch {
                onTaskCompletedStatusChange(
                    event.task,
                    event.isCompleted
                )
            }

            else -> Unit
        }
    }

    private suspend fun onTaskCompletedStatusChange(task: Task, completed: Boolean) {
        thesisRepository.changeTaskCompletedStatus(task, completed)
    }

    private suspend fun onSaveTaskClick(task: Task) {
        val updatedTask = task.copy(thesisId = thesisId)
        thesisRepository.addNewTask(updatedTask)
    }

    private fun onThesisTitleChange(thesisName: String) {
        _uiState.update {
            it.copy(
                editedThesisTitle = thesisName
            )
        }
        savingTitleJob = viewModelScope.launch {
            if (savingTitleJob?.isActive == true) savingTitleJob?.cancel()
            delay(debounceDuration)
            thesisRepository.updateThesisTitleById(thesisId, thesisName)
        }
    }


    private suspend fun onDeleteArticleClick(article: File) {
        val confirmationAlertDialog = AlertDialog.Confirmation(
            title = UIText.StringResource(R.string.delete_article),
            message = UIText.StringResource(
                R.string.are_you_sure_you_want_to_delete,
                article.fileName
            ),
        )
        showPopUp(confirmationAlertDialog)
        val result = confirmationAlertDialog.result.await()
        when (result) {
            AlertDialog.Result.Positive -> {
                dismissPopUp(confirmationAlertDialog)
                thesisRepository.updateThesis(thesis.thesis.copy(articles = thesis.thesis.articles - article))
            }

            AlertDialog.Result.Negative -> dismissPopUp(confirmationAlertDialog)
            AlertDialog.Result.Dismiss -> dismissPopUp(confirmationAlertDialog)
        }
    }

    private fun onDocumentPickerResult(articles: List<File>) {
//        _uiState.update { it.copy(articles = it.articles + articles) }
        viewModelScope.launch {
            articles.forEach {
                thesisRepository.updateThesis(
                    thesis = thesis.thesis.copy(articles = thesis.thesis.articles + it)
                )
            }
        }
    }

    private fun showPopUp(popUp: PopUp) {
        _uiState.update { it.copy(popUps = it.popUps + popUp) }
    }

    private fun dismissPopUp(popUp: PopUp) {
        _uiState.update { it.copy(popUps = it.popUps - popUp) }
    }

}