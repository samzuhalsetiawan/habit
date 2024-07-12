package com.wahyusembiring.habit.presentation.composable.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wahyusembiring.habit.domain.constant.SCRIM_DIM_OPACITY
import com.wahyusembiring.habit.presentation.screen.Screen
import com.wahyusembiring.habit.presentation.theme.spacing
import kotlin.reflect.KClass

@Composable
fun ProvideFloatingActionButton(
   routeSimpleClassName: String?,
   onClick: (fab: ClickedFAB) -> Unit
) {
   when (routeSimpleClassName) {
      Screen.Home::class.simpleName -> HomeFloatingActionButton(onClick)
      else -> Unit
   }
}

@Composable
private fun Scrim(
   isVisible: Boolean = false
) {
   val opacityAnimation by animateFloatAsState(
      label = "Scrim Opacity Animation",
      targetValue = if (isVisible) SCRIM_DIM_OPACITY else 0f
   )
   Box(
      modifier = Modifier
         .offset(
            x = MaterialTheme.spacing.Medium,
            y = MaterialTheme.spacing.Medium
         )
         .fillMaxSize()
         .background(MaterialTheme.colorScheme.scrim.copy(alpha = opacityAnimation))
   )
}

@Composable
private fun HomeFloatingActionButton(
   onFabClick: (fab: ClickedFAB) -> Unit
) {
   var isExpanded by remember { mutableStateOf(false) }

   Scrim(isVisible = isExpanded)
   MultiFloatingActionButton(
      mainFloatingActionButton = {
         MainFloatingActionButton(
            onClick = { isExpanded = !isExpanded },
            isExpanded = isExpanded
         )
      },
      subFloatingActionButton = {
         SubFloatingActionButton(
            isExpanded = isExpanded,
            onFabClick = {
               onFabClick(it)
               isExpanded = false
            }
         )
      }
   )
}

enum class ClickedFAB {
   REMINDER, EXAM, HOMEWORK
}
