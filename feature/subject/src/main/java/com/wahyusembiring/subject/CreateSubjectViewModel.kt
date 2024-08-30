package com.wahyusembiring.subject

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.common.util.launch
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.data.repository.SubjectRepository
import com.wahyusembiring.ui.component.popup.AlertDialog
import com.wahyusembiring.ui.component.popup.Picker
import com.wahyusembiring.ui.component.popup.PopUp
import com.wahyusembiring.ui.util.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateSubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateSubjectScreenUIState())
    val state = _state.asStateFlow()

    private val scope = viewModelScope

    fun onUIEvent(event: CreateSubjectScreenUIEvent) {
        when (event) {
            is CreateSubjectScreenUIEvent.OnSubjectNameChanged -> updateSubjectName(event.name)
            is CreateSubjectScreenUIEvent.OnRoomChanged -> updateRoom(event.room)
            is CreateSubjectScreenUIEvent.OnSaveButtonClicked -> launch { onSaveButtonClicked() }
            is CreateSubjectScreenUIEvent.OnPickColorButtonClicked -> launch { onPickColorButtonClicked() }
            else -> Unit
        }
    }

    private suspend fun onPickColorButtonClicked() {
        val colorPicker = Picker.ColorPicker()
        showPopUp(colorPicker)
        when (val result = colorPicker.result.await()) {
            is Picker.Result.Picked -> {
                hidePopUp(colorPicker)
                updateColor(result.value)
            }

            is Picker.Result.Dismiss -> hidePopUp(colorPicker)
        }
    }

    private suspend fun onSaveButtonClicked() {
        val confirmationDialog = AlertDialog.Confirmation(
            title = UIText.StringResource(R.string.save_subject),
            message = UIText.StringResource(R.string.are_you_sure_you_want_to_save_this_subject),
        )
        showPopUp(confirmationDialog)
        when (confirmationDialog.result.await()) {
            AlertDialog.Result.Positive -> {
                hidePopUp(confirmationDialog)
                onSaveSubjectConfirmed()
            }

            AlertDialog.Result.Negative -> hidePopUp(confirmationDialog)
            AlertDialog.Result.Dismiss -> hidePopUp(confirmationDialog)
        }
    }

    private fun onSaveSubjectConfirmed() {
        val loadingPopup = AlertDialog.Loading(UIText.StringResource(R.string.saving))
        viewModelScope.launch {
            try {
                showPopUp(loadingPopup)
                saveSubject()
                hidePopUp(loadingPopup)

                // Show success popup
                val successPopup = AlertDialog.Information(
                    message = UIText.StringResource(R.string.subject_saved)
                )
                showPopUp(successPopup)
                successPopup.result.invokeOnCompletion { hidePopUp(successPopup) }
            } catch (e: MissingRequiredFieldException) {
                hidePopUp(loadingPopup)
                val errorMessage = when (e) {
                    is MissingRequiredFieldException.SubjectName -> UIText.StringResource(R.string.subject_name_is_required)
                    is MissingRequiredFieldException.Room -> UIText.StringResource(R.string.room_is_required)
                }
                val errorPopup = AlertDialog.Error(errorMessage)
                showPopUp(errorPopup)
                errorPopup.result.invokeOnCompletion { hidePopUp(errorPopup) }
            }
        }
    }

    private suspend fun saveSubject() {
        val subject = Subject(
            name = _state.value.name.ifBlank { throw MissingRequiredFieldException.SubjectName() },
            color = _state.value.color,
            room = _state.value.room.ifBlank { throw MissingRequiredFieldException.Room() },
            description = _state.value.description,
            lecture = null
        )
        subjectRepository.saveSubject(subject)
    }

    private fun updateSubjectName(name: String) {
        _state.update {
            it.copy(name = name)
        }
    }

    private fun updateColor(color: Color) {
        _state.update {
            it.copy(color = color)
        }
    }

    private fun updateRoom(room: String) {
        _state.update {
            it.copy(room = room)
        }
    }

    private fun showPopUp(popUp: PopUp) {
        _state.update {
            it.copy(popUps = it.popUps + popUp)
        }
    }

    private fun hidePopUp(popUp: PopUp) {
        _state.update {
            it.copy(popUps = it.popUps - popUp)
        }
    }

}