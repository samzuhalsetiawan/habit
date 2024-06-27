package com.samzuhalsetiawan.habbits.ui.screen.menu_pertanyaan

import androidx.lifecycle.ViewModel
import com.samzuhalsetiawan.habbits.models.OpsiJawaban
import com.samzuhalsetiawan.habbits.models.Pertanyaan
import com.samzuhalsetiawan.habbits.singletons.ListPertanyaan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class MenuPertanyaanViewModel : ViewModel() {

   private val listPertanyaan = ListPertanyaan.listPertanyaan

   private val _state = MutableStateFlow(
      State(
         currentQuestion = listPertanyaan[0],
         selectedOption = null
      )
   )
   val state: StateFlow<State> = _state

   fun setSelectedOption(jawaban: OpsiJawaban) {
      _state.update {
         it.copy(
            currentQuestion = it.currentQuestion.apply { choise = jawaban },
            selectedOption = jawaban
         )
      }
   }

   fun onConfirmButtonClicked(
      pertanyaan: Pertanyaan,
      jawaban: String?,
      onNavigateToResult: () -> Unit,
      onQuestionNotAnswered: () -> Unit
   ) {
      if (jawaban == null) {
         onQuestionNotAnswered()
         return
      }
      pertanyaan.choise = pertanyaan.answers.find { it.jawaban == jawaban }
      if (listPertanyaan.last() == _state.value.currentQuestion) {
         onNavigateToResult()
         return
      }
      _state.update {
         val question = listPertanyaan[listPertanyaan.indexOf(it.currentQuestion) + 1]
         it.copy(
            currentQuestion = question,
            selectedOption = question.choise
         )
      }
   }

   fun onBackButtonClicked(onPopBackStackRequest: () -> Unit) {
      if (_state.value.currentQuestion == listPertanyaan.first()) {
         onPopBackStackRequest()
         return
      }
      _state.update {
         val question = listPertanyaan[listPertanyaan.indexOf(it.currentQuestion) - 1]
         it.copy(
            currentQuestion = question,
            selectedOption = question.choise
         )
      }
   }

   data class State(
      val currentQuestion: Pertanyaan,
      val selectedOption: OpsiJawaban?
   )
}