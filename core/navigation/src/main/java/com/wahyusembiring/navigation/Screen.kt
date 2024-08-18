package com.wahyusembiring.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

   @Serializable
   data object Overview : Screen()

   @Serializable
   data object CreateHomework : Screen()

   @Serializable
   data object CreateReminder : Screen()

   @Serializable
   data object CreateExam : Screen()

   @Serializable
   data object CreateSubject : Screen()

   @Serializable
   data object Timetable : Screen()

}