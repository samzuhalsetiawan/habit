package com.wahyusembiring.habit.feature.subject.presentation.screen.createsubject

sealed class MissingRequiredFieldException : Exception() {
   class SubjectName : MissingRequiredFieldException()
   class Room : MissingRequiredFieldException()
}