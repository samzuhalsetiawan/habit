package com.wahyusembiring.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingScreenViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnBoardingScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun onUIEvent(event: OnBoardingScreenUIEvent) {
        when (event) {
            is OnBoardingScreenUIEvent.OnCompleted -> {
                saveOnBoardingState(completed = true)
            }
        }
    }

    private fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveOnBoardingState(completed = completed)
        }
    }

}