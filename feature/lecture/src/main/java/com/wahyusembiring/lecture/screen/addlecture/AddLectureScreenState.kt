package com.wahyusembiring.lecture.screen.addlecture

import android.content.ContentResolver
import android.net.Uri
import androidx.navigation.NavController
import com.wahyusembiring.data.model.OfficeHour
import com.wahyusembiring.ui.util.UIText

data class AddLectureScreenUItate(
    val showSaveConfirmationDialog: Boolean = false,
    val errorMessage: UIText? = null,
    val name: String = "",
    val profilePictureUri: Uri? = null,
    val phoneNumbers: List<String> = emptyList(),
    val emails: List<String> = emptyList(),
    val addresses: List<String> = emptyList(),
    val officeHours: List<OfficeHour> = emptyList(),
    val websites: List<String> = emptyList()
)

sealed class AddLectureScreenUIEvent {
    data class OnBackButtonClick(val navController: NavController) : AddLectureScreenUIEvent()
    data class OnLectureNameChange(val name: String) : AddLectureScreenUIEvent()
    data object OnSaveButtonClick : AddLectureScreenUIEvent()
    data class OnProfilePictureSelected(val uri: Uri?) : AddLectureScreenUIEvent()
    data class OnNewPhoneNumber(val phoneNumber: String) : AddLectureScreenUIEvent()
    data class OnNewEmail(val email: String) : AddLectureScreenUIEvent()
    data class OnNewAddress(val address: String) : AddLectureScreenUIEvent()
    data class OnNewOfficeHour(val officeHour: OfficeHour) : AddLectureScreenUIEvent()
    data class OnNewWebsite(val website: String) : AddLectureScreenUIEvent()

    // Save Confirmation Dialog
    data object OnSaveConfirmationDialogDismiss : AddLectureScreenUIEvent()
    data object OnSaveConfirmationDialogConfirm : AddLectureScreenUIEvent()
    data object OnSaveConfirmationDialogCancel : AddLectureScreenUIEvent()

    // Error Dialog
    data object OnErrorDialogDismiss : AddLectureScreenUIEvent()
}