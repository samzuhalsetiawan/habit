package com.wahyusembiring.habit.feature.lecture.domain.model

import com.wahyusembiring.habit.core.domain.model.Time
import kotlinx.serialization.Serializable

@Serializable
data class OfficeHour(
   val day: Int,
   val startTime: Time,
   val endTime: Time
)