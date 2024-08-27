package com.wahyusembiring.kanban

import com.wahyusembiring.data.model.Event
import com.wahyusembiring.data.model.Subject

data class KanbanBoardScreenUIState(
    val todayEvents: Map<Event, Subject?> = emptyMap(),
    val tomorrowEvents: Map<Event, Subject?> = emptyMap(),
    val next2DaysEvents: Map<Event, Subject?> = emptyMap(),
    val next3DaysEvents: Map<Event, Subject?> = emptyMap(),
    val next4DaysEvents: Map<Event, Subject?> = emptyMap(),
    val next5DaysEvents: Map<Event, Subject?> = emptyMap(),
    val next6DaysEvents: Map<Event, Subject?> = emptyMap(),
)