package com.wahyusembiring.calendar

import com.wahyusembiring.data.model.Event
import com.wahyusembiring.data.model.Subject

data class CalendarScreenUIState(
    val events: Map<Event, Subject?> = emptyMap()
)