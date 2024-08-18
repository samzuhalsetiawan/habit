package com.wahyusembiring.overview

import com.wahyusembiring.data.model.Homework

data class OverviewScreenUIState(
   val homeworks: List<Homework> = emptyList(),
   val popUp: OverviewScreenPopUp? = null,
   val tabTitles: List<String> = emptyList()
)

sealed class OverviewScreenPopUp {
   data object OverviewLoading : OverviewScreenPopUp()
}