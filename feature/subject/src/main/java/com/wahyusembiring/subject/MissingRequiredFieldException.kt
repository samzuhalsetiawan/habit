package com.wahyusembiring.subject

sealed class MissingRequiredFieldException : Exception() {
   class SubjectName : MissingRequiredFieldException()
   class Room : MissingRequiredFieldException()
}