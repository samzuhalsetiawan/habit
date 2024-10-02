package com.wahyusembiring.kanban

import com.wahyusembiring.data.model.entity.Subject

data class KanbanBoardScreenUIState(
    val todayEvents: List<Any> = emptyList(),
    val tomorrowEvents: List<Any> = emptyList(),
    val next2DaysEvents: List<Any> = emptyList(),
    val next3DaysEvents: List<Any> = emptyList(),
    val next4DaysEvents: List<Any> = emptyList(),
    val next5DaysEvents: List<Any> = emptyList(),
    val next6DaysEvents: List<Any> = emptyList(),
)