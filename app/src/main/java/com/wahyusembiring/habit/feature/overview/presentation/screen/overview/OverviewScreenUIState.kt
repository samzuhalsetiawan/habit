package com.wahyusembiring.habit.feature.overview.presentation.screen.overview

import com.wahyusembiring.habit.feature.homework.domain.model.Homework

data class OverviewScreenUIState(
   val homeworks: List<Homework> = emptyList(),
   val popUp: OverviewScreenPopUp? = null,
   val tabTitles: List<String> = emptyList()
)

sealed class OverviewScreenPopUp {
   data object OverviewLoading : OverviewScreenPopUp()
}