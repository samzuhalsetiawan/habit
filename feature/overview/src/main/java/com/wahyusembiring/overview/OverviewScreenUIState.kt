package com.wahyusembiring.overview

import com.wahyusembiring.data.model.Event
import com.wahyusembiring.data.model.Subject
import com.wahyusembiring.overview.component.eventcard.EventCard

data class OverviewScreenUIState(
    val eventCards: List<EventCard> = emptyList(),
    val popUp: OverviewScreenPopUp? = null,
)

sealed class OverviewScreenPopUp {
    data object OverviewLoading : OverviewScreenPopUp()
}