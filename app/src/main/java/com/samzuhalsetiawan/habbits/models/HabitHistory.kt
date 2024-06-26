package com.samzuhalsetiawan.habbits.models

data class HabitHistory(
    val status: Status = Status.SKIPPED,
    val timestamp: Long = 0L
) {
    enum class Status {
        COMPLETE,
        SKIPPED,
        FAILED
    }
}