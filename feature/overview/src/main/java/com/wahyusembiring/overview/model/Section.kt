package com.wahyusembiring.overview.model

data class Section(
    val title: String,
    val date: String,
    val events: List<Event>
)