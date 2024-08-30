package com.wahyusembiring.overview

import com.wahyusembiring.common.navigation.Screen
import com.wahyusembiring.data.model.Event


sealed class OverviewScreenUIEvent {
    data class OnMarkEventAsCompleted(val event: Event) : OverviewScreenUIEvent()
    data class OnMarkEventAsUncompleted(val event: Event) : OverviewScreenUIEvent()
    data object OnHamburgerMenuClick : OverviewScreenUIEvent()
    data class OnNavigateTo(val screen: Screen) : OverviewScreenUIEvent()
}