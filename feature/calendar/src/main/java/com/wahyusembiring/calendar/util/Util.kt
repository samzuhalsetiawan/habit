package com.wahyusembiring.calendar.util

import com.wahyusembiring.data.model.Event
import com.wahyusembiring.data.model.Exam
import com.wahyusembiring.data.model.Homework
import com.wahyusembiring.data.model.Reminder
import com.wahyusembiring.data.model.Subject
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun Map<Event, Subject?>.getEventsByDate(date: LocalDate): Map<Event, Subject?> {
    return this.filter {
        val eventDate = when (it.key) {
            is Exam -> (it.key as Exam).date
            is Homework -> (it.key as Homework).dueDate
            is Reminder -> (it.key as Reminder).date
        }
        val eventLocalDate =
            Instant.ofEpochMilli(eventDate.time).atZone(ZoneId.systemDefault()).toLocalDate()
        eventLocalDate == date
    }
}