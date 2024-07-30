package com.wahyusembiring.habit.feature.homework.presentation.screen.createhomework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyusembiring.habit.core.domain.model.Attachment
import com.wahyusembiring.habit.core.domain.model.Time
import com.wahyusembiring.habit.feature.homework.domain.model.Homework
import com.wahyusembiring.habit.feature.homework.domain.repository.HomeworkRepository
import com.wahyusembiring.habit.feature.subject.domain.model.Subject
import com.wahyusembiring.habit.feature.subject.domain.repository.SubjectRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class CreateHomeworkScreenViewModel(
   private val homeworkRepository: HomeworkRepository,
   private val subjectRepository: SubjectRepository
) : ViewModel() {

   private var getAllSubjectJob: Job? = null

   private val _state = MutableStateFlow(CreateHomeworkScreenUIState())
   val state = _state.asStateFlow()

   fun onUIEvent(event: CreateHomeworkUIEvent) {
      when (event) {
         is CreateHomeworkUIEvent.OnHomeworkTitleChanged -> onHomeworkTitleChanged(event.title)
         is CreateHomeworkUIEvent.OnDateSelected -> onDateSelected(event.date)
         is CreateHomeworkUIEvent.OnTimeSelected -> onTimeSelected(event.time)
         is CreateHomeworkUIEvent.OnSubjectSelected -> onSubjectSelected(event.subject)
         is CreateHomeworkUIEvent.OnAttachmentsConfirmed -> onAttachmentsConfirmed(event.attachments)
         is CreateHomeworkUIEvent.OnSaveHomeworkButtonClicked -> onSaveHomework()
         is CreateHomeworkUIEvent.OnPickDateButtonClicked -> showPopUp(CreateHomeworkScreenPopUp.DatePicker)
         is CreateHomeworkUIEvent.OnPickTimeButtonClicked -> showPopUp(CreateHomeworkScreenPopUp.TimePicker)
         is CreateHomeworkUIEvent.OnPickSubjectButtonClicked -> showPopUp(CreateHomeworkScreenPopUp.SubjectPicker)
         is CreateHomeworkUIEvent.HidePopUp -> hidePopUp(event.popUp)
         is CreateHomeworkUIEvent.OnPickAttachmentButtonClicked -> showPopUp(
            CreateHomeworkScreenPopUp.AttachmentPicker
         )

      }
   }

   private fun onSaveHomework() {
      viewModelScope.launch {
         try {
            _state.update {
               it.copy(popUp = CreateHomeworkScreenPopUp.SaveHomeworkLoading)
            }
            saveHomework()
            _state.update {
               it.copy(popUp = CreateHomeworkScreenPopUp.SaveHomeworkSuccess)
            }
         } catch (e: MissingRequiredFieldException) {
            when (e) {
               is MissingRequiredFieldException.Title -> _state.update {
                  it.copy(popUp = CreateHomeworkScreenPopUp.HomeworkTitleIsRequired)
               }

               is MissingRequiredFieldException.Date -> _state.update {
                  it.copy(popUp = CreateHomeworkScreenPopUp.DateIsRequired)
               }

               is MissingRequiredFieldException.Subject -> _state.update {
                  it.copy(popUp = CreateHomeworkScreenPopUp.SubjectIsRequired)
               }
            }
         }
      }
   }

   private suspend fun saveHomework() {
      val homework = Homework(
         title = _state.value.homeworkTitle.ifBlank { throw MissingRequiredFieldException.Title() },
         dueDate = _state.value.date ?: throw MissingRequiredFieldException.Date(),
         subject = _state.value.subject?.id ?: throw MissingRequiredFieldException.Subject(),
         reminder = _state.value.time,
         description = _state.value.description
      )
      homeworkRepository.saveHomework(homework, _state.value.attachments)
   }

   private fun showPopUp(popUp: CreateHomeworkScreenPopUp) {
      _state.update {
         it.copy(popUp = popUp)
      }
   }

   private fun hidePopUp(popUp: CreateHomeworkScreenPopUp) {
      _state.update {
         it.copy(popUp = null)
      }
   }

   private fun onHomeworkTitleChanged(title: String) {
      _state.update {
         it.copy(homeworkTitle = title)
      }
   }

   private fun onDateSelected(date: Date) {
      _state.update {
         it.copy(date = date)
      }
   }

   private fun onTimeSelected(time: Time) {
      _state.update {
         it.copy(time = time)
      }
   }

   private fun onSubjectSelected(subject: Subject) {
      _state.update {
         it.copy(subject = subject)
      }
   }

   private fun onAttachmentsConfirmed(attachments: List<Attachment>) {
      _state.update {
         it.copy(attachments = attachments)
      }
   }

   init {
      getAllSubjectJob = viewModelScope.launch {
         subjectRepository.getAllSubjectsAsFlow().collect { subjects ->
            _state.update {
               it.copy(subjects = subjects)
            }
         }
      }
   }

   override fun onCleared() {
      getAllSubjectJob?.cancel()
      getAllSubjectJob = null
      super.onCleared()
   }

}