package com.wahyusembiring.overview.model

import com.wahyusembiring.data.model.Exam
import com.wahyusembiring.data.model.Homework
import com.wahyusembiring.data.model.Reminder
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.overview.R
import com.wahyusembiring.ui.util.UIText

data class Event(
    val id: Int,
    val key: String,
    val title: String,
    val subject: Subject,
    val isCompleted: Boolean,
    val eventType: EventType,
)

enum class EventType(
    val displayName: UIText
) {
    EXAM(
        displayName = UIText.StringResource(R.string.exam)
    ),
    HOMEWORK(
        displayName = UIText.StringResource(R.string.homework)
    ),
    REMINDER(
        displayName = UIText.StringResource(R.string.reminder)
    )
}

internal fun Homework.toEvent(
    subject: Subject
) = Event(
    id = id,
    key = "${Homework::class.simpleName}/$id",
    title = title,
    subject = subject,
    isCompleted = completed,
    eventType = EventType.HOMEWORK
)

internal fun Exam.toEvent(
    subject: Subject
) = Event(
    id = id,
    key = "${Exam::class.simpleName}/$id",
    title = title,
    subject = subject,
    isCompleted = completed,
    eventType = EventType.EXAM
)

internal fun Reminder.toEvent(
    subject: Subject
) = Event(
    id = id,
    key = "${Reminder::class.simpleName}/$id",
    title = title,
    subject = subject,
    isCompleted = completed,
    eventType = EventType.REMINDER
)