package com.wahyusembiring.overview

import com.wahyusembiring.data.model.Event
import com.wahyusembiring.data.model.Exam
import com.wahyusembiring.data.model.Homework
import com.wahyusembiring.data.model.Reminder
import com.wahyusembiring.data.model.Subject

data class OverviewScreenUIState(
    val todayEvents: Map<Event, Subject?> = emptyMap(),
    val tomorrowEvents: Map<Event, Subject?> = emptyMap(),
    val next2DaysEvents: Map<Event, Subject?> = emptyMap(),
    val next3DaysEvents: Map<Event, Subject?> = emptyMap(),
    val next4DaysEvents: Map<Event, Subject?> = emptyMap(),
    val next5DaysEvents: Map<Event, Subject?> = emptyMap(),
    val next6DaysEvents: Map<Event, Subject?> = emptyMap(),
    val popUp: OverviewScreenPopUp? = null,
)

sealed class OverviewScreenPopUp {
    data object OverviewLoading : OverviewScreenPopUp()
}