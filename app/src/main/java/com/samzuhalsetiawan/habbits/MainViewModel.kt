package com.samzuhalsetiawan.habbits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpManager
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(
   mainRepository: MainRepository,
) : ViewModel(),
   PopUpManager {

   private val _startDestination = MutableStateFlow<Screens>(Screens.Welcome)
   val startDestination: StateFlow<Screens> = _startDestination

   private val _popUpDialogState = MutableStateFlow<PopUpDialog?>(null)
   val popUpDialogState: StateFlow<PopUpDialog?> = _popUpDialogState

   val isNewUser = mainRepository.getIsNewUserFlow()

   val isLogin = mainRepository.getIsLoginFlow()

   init {
      viewModelScope.launch {
         launch { setupSplashScreenAndAppInitialization() }
      }
   }

   override fun showPopUpDialog(dialog: PopUpDialog) {
      _popUpDialogState.update { dialog }
   }

   override fun closePopUpDialog() {
      _popUpDialogState.update { null }
   }

   private suspend fun setupSplashScreenAndAppInitialization() {
      delay(1000L)
      isAppReady = true
   }

   companion object {
      var isAppReady = false
   }
}