package com.wahyusembiring.common.navigation

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
    data object KanbanBoard : Screen()

    @Serializable
    data object Timetable : Screen()

    @Serializable
    data object Calendar : Screen()

    @Serializable
    data object OnBoarding : Screen()

}