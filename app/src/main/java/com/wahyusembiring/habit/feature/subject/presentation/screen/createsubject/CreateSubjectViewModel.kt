package com.wahyusembiring.habit.feature.subject.presentation.screen.createsubject

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.habit.feature.subject.domain.model.Subject
import com.wahyusembiring.habit.feature.subject.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateSubjectViewModel(
   private val subjectRepository: SubjectRepository
) : ViewModel() {

   private val _state = MutableStateFlow(CreateSubjectScreenUIState())
   val state = _state.asStateFlow()

   fun onUIEvent(event: CreateSubjectScreenUIEvent) {
      when (event) {
         is CreateSubjectScreenUIEvent.OnSubjectNameChanged -> updateSubjectName(event.name)
         is CreateSubjectScreenUIEvent.OnColorConfirmed -> updateColor(event.color)
         is CreateSubjectScreenUIEvent.OnRoomChanged -> updateRoom(event.room)
         is CreateSubjectScreenUIEvent.HidePopUp -> hidePopUp(event.popUp)
         is CreateSubjectScreenUIEvent.OnPickColorButtonClicked -> showPopUp(
            CreateSubjectScreenPopUp.ColorPicker
         )

         is CreateSubjectScreenUIEvent.OnSaveButtonClicked -> onSaveSubject()
      }
   }

   private fun onSaveSubject() {
      viewModelScope.launch {
         try {
            showPopUp(CreateSubjectScreenPopUp.SaveSubjectLoading)
            saveSubject()
            showPopUp(CreateSubjectScreenPopUp.SaveSubjectSuccess)
         } catch (e: MissingRequiredFieldException) {
            when (e) {
               is MissingRequiredFieldException.SubjectName -> showPopUp(CreateSubjectScreenPopUp.NameIsRequired)
               is MissingRequiredFieldException.Room -> showPopUp(CreateSubjectScreenPopUp.RoomIsRequired)
            }
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

   fun showPopUp(popUp: CreateSubjectScreenPopUp) {
      _state.update {
         it.copy(popUp = popUp)
      }
   }

   fun hidePopUp(popUp: CreateSubjectScreenPopUp) {
      _state.update {
         it.copy(popUp = null)
      }
   }

}