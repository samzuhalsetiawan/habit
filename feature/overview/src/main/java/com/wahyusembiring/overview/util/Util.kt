package com.wahyusembiring.overview.util

import com.wahyusembiring.data.model.Event
import com.wahyusembiring.data.model.Exam
import com.wahyusembiring.data.model.Homework
import com.wahyusembiring.data.model.Reminder
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.datetime.Moment
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

infix fun Map<Event, Subject?>.inside(range: ClosedRange<Duration>): Map<Event, Subject?> {
    val today = Moment.now()
    val startDay = (today + range.start).epochMilliseconds
    val endDay = (today + range.endInclusive).epochMilliseconds

    return filter { entry ->
        when (val event = entry.key) {
            is Exam -> event.date.time in startDay..endDay
            is Homework -> event.dueDate.time in startDay..endDay
            is Reminder -> event.date.time in startDay..endDay
        }
    }
}

infix fun Duration.until(duration: Duration): ClosedRange<Duration> = (this..duration)