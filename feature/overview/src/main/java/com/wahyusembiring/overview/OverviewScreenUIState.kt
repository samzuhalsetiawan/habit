package com.wahyusembiring.overview

import com.wahyusembiring.data.model.Exam
import com.wahyusembiring.data.model.Homework
import com.wahyusembiring.data.model.Reminder
import com.wahyusembiring.overview.model.Event
import com.wahyusembiring.overview.model.Section

data class OverviewScreenUIState(
    val todayEvents: List<Event> = emptyList(),
    val tomorrowEvents: List<Event> = emptyList(),
    val next2DaysEvents: List<Event> = emptyList(),
    val next3DaysEvents: List<Event> = emptyList(),
    val next4DaysEvents: List<Event> = emptyList(),
    val next5DaysEvents: List<Event> = emptyList(),
    val next6DaysEvents: List<Event> = emptyList(),
    val popUp: OverviewScreenPopUp? = null,
)

sealed class OverviewScreenPopUp {
    data object OverviewLoading : OverviewScreenPopUp()
}