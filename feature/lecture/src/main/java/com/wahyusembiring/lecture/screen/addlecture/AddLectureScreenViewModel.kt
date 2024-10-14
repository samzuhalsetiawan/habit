package com.wahyusembiring.lecture.screen.addlecture

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.wahyusembiring.data.model.OfficeHour
import com.wahyusembiring.data.model.entity.Lecturer
import com.wahyusembiring.data.repository.LecturerRepository
import com.wahyusembiring.lecture.R
import com.wahyusembiring.ui.util.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLectureScreenViewModel @Inject constructor(
    private val lecturerRepository: LecturerRepository,
    private val application: Application
) : ViewModel() {

    private val _state = MutableStateFlow(AddLectureScreenUItate())
    val state = _state.asStateFlow()

    fun onUIEvent(event: AddLectureScreenUIEvent) {
        when (event) {
            is AddLectureScreenUIEvent.OnBackButtonClick -> onBackButtonClick(event.navController)
            is AddLectureScreenUIEvent.OnSaveButtonClick -> onSaveButtonClick()
            is AddLectureScreenUIEvent.OnLectureNameChange -> onLectureNameChange(event.name)
            is AddLectureScreenUIEvent.OnSaveConfirmationDialogCancel -> onSaveConfirmationDialogCancel()
            is AddLectureScreenUIEvent.OnSaveConfirmationDialogConfirm -> onSaveConfirmationDialogConfirm()
            is AddLectureScreenUIEvent.OnSaveConfirmationDialogDismiss -> onSaveConfirmationDialogDismiss()
            is AddLectureScreenUIEvent.OnErrorDialogDismiss -> onErrorDialogDismiss()
            is AddLectureScreenUIEvent.OnProfilePictureSelected -> onProfilePictureSelected(event.uri)
            is AddLectureScreenUIEvent.OnNewPhoneNumber -> onNewPhoneNumber(event.phoneNumber)
            is AddLectureScreenUIEvent.OnNewEmail -> onNewEmail(event.email)
            is AddLectureScreenUIEvent.OnNewAddress -> onNewAddress(event.address)
            is AddLectureScreenUIEvent.OnNewOfficeHour -> onNewOfficeHour(event.officeHour)
            is AddLectureScreenUIEvent.OnNewWebsite -> onNewWebsite(event.website)
            is AddLectureScreenUIEvent.OnLectureSavedDialogDismiss -> onLectureSavedDialogDismiss()
        }
    }

    private fun onLectureSavedDialogDismiss() {
        _state.update {
            it.copy(showLectureSavedDialog = false)
        }
    }

    private fun onNewWebsite(website: String) {
        _state.update {
            it.copy(websites = it.websites + website)
        }
    }

    private fun onNewOfficeHour(officeHour: OfficeHour) {
        _state.update {
            it.copy(officeHours = it.officeHours + officeHour)
        }
    }

    private fun onNewAddress(address: String) {
        _state.update {
            it.copy(
                addresses = it.addresses + address
            )
        }
    }

    private fun onNewEmail(email: String) {
        _state.update {
            it.copy(
                emails = it.emails + email
            )
        }
    }

    private fun onNewPhoneNumber(phoneNumber: String) {
        _state.update {
            it.copy(
                phoneNumbers = it.phoneNumbers + phoneNumber
            )
        }
    }

    private fun onProfilePictureSelected(uri: Uri?) {
        _state.update {
            it.copy(
                profilePictureUri = uri ?: it.profilePictureUri
            )
        }
    }

    private fun onErrorDialogDismiss() {
        _state.update {
            it.copy(errorMessage = null)
        }
    }

    private fun onSaveConfirmationDialogConfirm() {
        onSaveConfirmationDialogDismiss()
        try {
            val lecture = Lecturer(
                photo = _state.value.profilePictureUri.also {
                    if (it != null) {
                        application.applicationContext.contentResolver
                            .takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                },
                name = _state.value.name.ifBlank {
                    throw ValidationException(
                        UIText.StringResource(R.string.lecture_name_cannot_be_empty)
                    )
                },
                phone = _state.value.phoneNumbers,
                email = _state.value.emails,
                address = _state.value.addresses,
                officeHour = _state.value.officeHours,
                website = _state.value.websites,
            )
            viewModelScope.launch {
                lecturerRepository.insertLecture(lecture)
                _state.update { it.copy(showLectureSavedDialog = true) }
            }
        } catch (validationException: ValidationException) {
            _state.update {
                it.copy(errorMessage = validationException.displayMessage)
            }
        }
    }

    private fun onSaveConfirmationDialogDismiss() {
        _state.update {
            it.copy(showSaveConfirmationDialog = false)
        }
    }

    private fun onSaveConfirmationDialogCancel() {
        _state.update {
            it.copy(showSaveConfirmationDialog = false)
        }
    }

    private fun onLectureNameChange(name: String) {
        _state.update {
            it.copy(name = name)
        }
    }

    private fun onSaveButtonClick() {
        _state.update {
            it.copy(showSaveConfirmationDialog = true)
        }
    }

    private fun onBackButtonClick(navController: NavController) {
        navController.navigateUp()
    }
}