package com.wahyusembiring.overview

import com.wahyusembiring.overview.model.Event

sealed class OverviewScreenUIEvent {
    data class OnMarkEventAsCompleted(val event: Event) : OverviewScreenUIEvent()
    data class OnMarkEventAsUncompleted(val event: Event) : OverviewScreenUIEvent()
}