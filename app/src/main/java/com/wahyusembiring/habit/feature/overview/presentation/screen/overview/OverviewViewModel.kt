package com.wahyusembiring.habit.feature.overview.presentation.screen.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.feature.homework.domain.repository.HomeworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
   private val homeworkRepository: HomeworkRepository
) : ViewModel() {

   private var getAllHomeworkJob: Job? = null

   private val _state = MutableStateFlow(OverviewScreenUIState())
   val state: StateFlow<OverviewScreenUIState> = _state

   fun onUIEvent(event: OverviewScreenUIEvent) {

   }

   private fun showPopUp(popUp: OverviewScreenPopUp) {
      _state.update { it.copy(popUp = popUp) }
   }

   private fun hidePopUp(popUp: OverviewScreenPopUp) {
      _state.update { it.copy(popUp = null) }
   }

   init {
      getAllHomeworkJob = viewModelScope.launch { getAllHomework() }
   }

   private suspend fun getAllHomework() {
      showPopUp(OverviewScreenPopUp.OverviewLoading)
      delay(2000)
      val flow = homeworkRepository.getAllHomeworkAsFlow()
      hidePopUp(OverviewScreenPopUp.OverviewLoading)
      flow.collect { homeworks ->
         _state.update { it.copy(homeworks = homeworks) }
      }
   }

   override fun onCleared() {
      getAllHomeworkJob?.cancel()
      getAllHomeworkJob = null
      super.onCleared()
   }

}