package com.wahyusembiring.habit.presentation.composable.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.domain.constant.SCRIM_DIM_OPACITY
import com.wahyusembiring.habit.presentation.theme.spacing

private const val SCRIM_LAYOUT_ID = "scrim"
private const val SUB_FAB_COLUMN_ID = "fab_column"

@Composable
fun MultiFloatingActionButton(
   modifier: Modifier = Modifier,
) {
   var isExpanded by remember { mutableStateOf(false) }

   MultiFloatingActionButtonLayout {
      Scrim(
         modifier = Modifier.layoutId(SCRIM_LAYOUT_ID),
         isShowing = isExpanded
      )
      MainFloatingActionButton(
         modifier = modifier,
         onClick = {
            isExpanded = !isExpanded
         },
         isExpanded = isExpanded
      )
      Column(
         modifier = Modifier
            .layoutId(SUB_FAB_COLUMN_ID)
            .padding(bottom = MaterialTheme.spacing.Small)
      ) {
         SubFloatingActionButton(
            isVisible = isExpanded,
            onClick = { /*TODO*/ },
         ) {
            Icon(
               painter = painterResource(id = R.drawable.ic_reminder),
               contentDescription = stringResource(R.string.add_reminder)
            )
         }
         SubFloatingActionButton(
            isVisible = isExpanded,
            onClick = { /*TODO*/ },
         ) {
            Icon(
               painter = painterResource(id = R.drawable.ic_exam),
               contentDescription = stringResource(R.string.add_exam_schedule)
            )
         }
         SubFloatingActionButton(
            isVisible = isExpanded,
            onClick = { /*TODO*/ },
         ) {
            Icon(
               painter = painterResource(id = R.drawable.ic_homework),
               contentDescription = stringResource(R.string.add_homework)
            )
         }
      }
   }
}

@Composable
private fun MultiFloatingActionButtonLayout(
   content: @Composable () -> Unit
) {
   Layout(
      content = content
   ) { measurables, constraints ->
      val scrimMeasurable = measurables.first { it.layoutId == SCRIM_LAYOUT_ID }
      val subFabColumnMeasurable = measurables.first { it.layoutId == SUB_FAB_COLUMN_ID }
      val mainFabMeasurables =
         measurables.first { it != scrimMeasurable && it != subFabColumnMeasurable }
      val scrimPlaceable = scrimMeasurable.measure(constraints)
      val subFabColumnPlaceable = subFabColumnMeasurable.measure(constraints)
      val mainFabPlaceables = mainFabMeasurables.measure(constraints)

      layout(
         width = constraints.maxWidth,
         height = constraints.maxHeight
      ) {
         scrimPlaceable.placeRelative(
            constraints.minWidth - (coordinates?.positionInRoot()?.x?.toInt() ?: 0),
            constraints.minHeight - (coordinates?.positionInRoot()?.y?.toInt() ?: 0)
         )
         mainFabPlaceables.placeRelative(
            constraints.maxWidth - mainFabPlaceables.width,
            constraints.maxHeight - mainFabPlaceables.height
         )
         subFabColumnPlaceable.placeRelative(
            constraints.maxWidth - subFabColumnPlaceable.width,
            constraints.maxHeight - (subFabColumnPlaceable.height + mainFabPlaceables.height)
         )
      }
   }
}

@Composable
private fun MainFloatingActionButton(
   modifier: Modifier = Modifier,
   onClick: () -> Unit,
   isExpanded: Boolean
) {
   FloatingActionButton(
      modifier = modifier,
      onClick = onClick
   ) {
      val animatedDegree by animateFloatAsState(
         label = "Icon Rotation Animation",
         targetValue = if (isExpanded) 135f else 0f,
      )
      Icon(
         modifier = Modifier.rotate(animatedDegree),
         painter = painterResource(id = R.drawable.ic_add),
         contentDescription = stringResource(R.string.create_task)
      )
   }
}

@Composable
private fun SubFloatingActionButton(
   modifier: Modifier = Modifier,
   onClick: () -> Unit = {},
   isVisible: Boolean,
   icon: @Composable () -> Unit,
) {
   AnimatedVisibility(
      visible = isVisible,
      enter = fadeIn() + expandIn(spring(Spring.DampingRatioLowBouncy, Spring.StiffnessMediumLow)),
      exit = shrinkOut() + fadeOut()
   ) {
      FloatingActionButton(
         modifier = modifier
            .scale(0.8f),
         onClick = onClick,
         content = icon
      )
   }
}

@Composable
private fun Scrim(
   modifier: Modifier = Modifier,
   isShowing: Boolean
) {
   val animatedOpacity by animateFloatAsState(
      label = "Scrim Opacity Animation",
      targetValue = if (isShowing) SCRIM_DIM_OPACITY else 0f
   )
   Box(
      modifier = modifier
         .zIndex(-1f)
         .fillMaxSize()
         .background(
            color = MaterialTheme.colorScheme.scrim.copy(alpha = animatedOpacity)
         )
   )
}