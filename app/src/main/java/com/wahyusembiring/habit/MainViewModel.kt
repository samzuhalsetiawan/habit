package com.wahyusembiring.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.common.navigation.Screen
import com.wahyusembiring.common.util.launch
import com.wahyusembiring.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _isAppReady = MutableStateFlow(false)
    val isAppReady: StateFlow<Boolean> = _isAppReady

    private val _startDestination: MutableStateFlow<Screen> = MutableStateFlow(Screen.Blank)
    val startDestination: StateFlow<Screen> = _startDestination

    init {
        viewModelScope.launch {
            initializeApp()
        }
    }

    private suspend fun initializeApp() {
        launch {
            dataStoreRepository.readOnBoardingState().collect { completed ->
                if (completed) {
                    _startDestination.update { Screen.Overview }
                } else {
                    _startDestination.update { Screen.OnBoarding }
                }
            }
        }
        delay(3000L)
        _isAppReady.value = true
    }

}