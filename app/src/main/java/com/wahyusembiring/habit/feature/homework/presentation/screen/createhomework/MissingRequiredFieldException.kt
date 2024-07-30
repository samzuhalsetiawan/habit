package com.wahyusembiring.habit.feature.homework.presentation.screen.createhomework

sealed class MissingRequiredFieldException : Exception() {
   class Title : MissingRequiredFieldException()
   class Date : MissingRequiredFieldException()
   class Subject : MissingRequiredFieldException()
}