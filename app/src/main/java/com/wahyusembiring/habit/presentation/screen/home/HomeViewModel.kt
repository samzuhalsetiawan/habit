package com.wahyusembiring.habit.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.habit.domain.model.Task
import com.wahyusembiring.habit.domain.utils.DummyData
import com.wahyusembiring.habit.presentation.composable.alertdialog.AlertDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// TODO: Extract hard coded string
class HomeViewModel : ViewModel(), HomeUserAction {

   private val _state = MutableStateFlow(HomeScreenState())
   val state: StateFlow<HomeScreenState> = _state

   init {
      viewModelScope.launch {
         showLoadingDialog("Getting task...")
         delay(2000L)
         _state.update {
            it.copy(
               tasks = DummyData.dummyTasks
            )
         }
         dissmissAlertDialog()
      }
   }

   override fun onTaskClicked(task: Task) {

   }

   override fun onDeleteTaskRequest(task: Task) {
      _state.update {
         it.copy(
            alertDialog = AlertDialog.Confirm(
               title = "Delete Task",
               message = "Are you sure you want to delete this task?",
               positiveButtonText = "Yes",
               negativeButtonText = "No",
               onPositiveButtonClick = {
                  deleteTask(task)
                  dissmissAlertDialog()
               },
               onNegativeButtonClick = { dissmissAlertDialog() }
            )
         )
      }
   }

   override fun dissmissAlertDialog() {
      _state.update {
         it.copy(
            alertDialog = null
         )
      }
   }

   private fun deleteTask(task: Task) {

   }

   private fun showLoadingDialog(message: String) {
      _state.update {
         it.copy(
            alertDialog = AlertDialog.Loading(message)
         )
      }
   }


}