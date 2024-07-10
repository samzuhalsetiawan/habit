package com.wahyusembiring.habit.presentation.composable.fab

import android.content.Context
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.domain.constant.SCRIM_DIM_OPACITY
import com.wahyusembiring.habit.domain.utils.GetterUtil
import com.wahyusembiring.habit.presentation.theme.spacing
import kotlin.math.abs
import kotlin.math.roundToInt

private const val SUB_FAB_COLUMN_ID = "fab_column"
private const val MAIN_FAB_LAYOUT_ID = "main_fab"

enum class FAB {
   REMINDER, EXAM, HOMEWORK
}

@Composable
fun MultiFloatingActionButton(
   modifier: Modifier = Modifier
) {
   var isExpanded by remember { mutableStateOf(false) }

   MultiFloatingActionButton(
      modifier = modifier,
      isExpanded = isExpanded,
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

            }
         )
      }
   )
}

@Composable
private fun MultiFloatingActionButton(
   modifier: Modifier = Modifier,
   isExpanded: Boolean,
   mainFloatingActionButton: @Composable () -> Unit,
   subFloatingActionButton: @Composable ColumnScope.() -> Unit
) {
   Box(
      modifier = Modifier
         .fillMaxSize()
         .zIndex(10f),
      contentAlignment = Alignment.BottomEnd
   ) {
      Scrim(isShowing = isExpanded)
      MultiFloatingActionButtonLayout(
         modifier = modifier
      ) {
         Column(
            modifier = Modifier
               .layoutId(SUB_FAB_COLUMN_ID)
         ) {
            subFloatingActionButton()
         }
         Box(
            modifier = Modifier
               .layoutId(MAIN_FAB_LAYOUT_ID)
               .padding(MaterialTheme.spacing.Medium)
         ) {
            mainFloatingActionButton()
         }
      }
   }
}

@Composable
private fun MultiFloatingActionButtonLayout(
   modifier: Modifier = Modifier,
   content: @Composable () -> Unit
) {
   Layout(
      modifier = modifier,
      content = content
   ) { measurables, constraints ->
      val subFabColumnMeasurable = measurables.first { it.layoutId == SUB_FAB_COLUMN_ID }
      val mainFabMeasurables = measurables.first { it.layoutId == MAIN_FAB_LAYOUT_ID }
      val subFabColumnPlaceable = subFabColumnMeasurable.measure(constraints)
      val mainFabPlaceables = mainFabMeasurables.measure(constraints)

      layout(
         width = constraints.maxWidth,
         height = constraints.maxHeight
      ) {
         val subFabColumnWidth = subFabColumnPlaceable.width
         val mainFabWidth = mainFabPlaceables.width
         val largestWidth = maxOf(subFabColumnWidth, mainFabWidth)
         val widthDifference = abs(subFabColumnWidth - mainFabWidth)
         val xOffset = (widthDifference / 2f).roundToInt()

         subFabColumnPlaceable.placeRelative(
            x = constraints.maxWidth - largestWidth + (if (subFabColumnWidth >= mainFabWidth) 0 else xOffset),
            y = constraints.maxHeight - (mainFabPlaceables.height + subFabColumnPlaceable.height)
         )

         mainFabPlaceables.placeRelative(
            x = constraints.maxWidth - largestWidth + (if (subFabColumnWidth > mainFabWidth) xOffset else 0),
            y = constraints.maxHeight - mainFabPlaceables.height
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
private fun ColumnScope.SubFloatingActionButton(
   modifier: Modifier = Modifier,
   isExpanded: Boolean,
   onFabClick: (subFab: FAB) -> Unit,
) {
   SubFloatingActionButton(
      isVisible = isExpanded,
      onClick = { onFabClick(FAB.REMINDER) },
   ) {
      Icon(
         painter = painterResource(id = R.drawable.ic_reminder),
         contentDescription = stringResource(R.string.add_reminder)
      )
   }
   SubFloatingActionButton(
      isVisible = isExpanded,
      onClick = { onFabClick(FAB.EXAM) },
   ) {
      Icon(
         painter = painterResource(id = R.drawable.ic_exam),
         contentDescription = stringResource(R.string.add_exam_schedule)
      )
   }
   SubFloatingActionButton(
      isVisible = isExpanded,
      onClick = { onFabClick(FAB.HOMEWORK) },
   ) {
      Icon(
         painter = painterResource(id = R.drawable.ic_homework),
         contentDescription = stringResource(R.string.add_homework)
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
   val context = LocalContext.current
   val windowSize = GetterUtil.getWindowSize(context)
   val windowWidth = windowSize.width
   val windowHeight = windowSize.height
   println(windowWidth)
   println(windowHeight)

   val animatedOpacity by animateFloatAsState(
      label = "Scrim Opacity Animation",
      targetValue = if (isShowing) SCRIM_DIM_OPACITY else 0f
   )

   Box(
      modifier = modifier
         .offset(y = (-10).dp)
         .size(windowWidth.dp, windowHeight.dp)
         .border(10.dp, Color.Red)
         .background(
            color = MaterialTheme.colorScheme.scrim.copy(alpha = animatedOpacity)
         )
   )
}