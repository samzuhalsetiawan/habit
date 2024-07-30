package com.wahyusembiring.habit.navigation

import android.view.animation.PathInterpolator
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.wahyusembiring.habit.core.presentation.Screen
import com.wahyusembiring.habit.feature.homework.presentation.screen.createhomework.createHomeworkScreen
import com.wahyusembiring.habit.feature.subject.presentation.screen.createsubject.createSubjectScreen
import com.wahyusembiring.habit.feature.overview.presentation.screen.overview.overviewScreen

@Composable
fun MainNavigation(
   navController: NavHostController,
   scaffoldPadding: PaddingValues
) {
   val emphasizedDeceleratedEasing = remember {
      Easing { PathInterpolator(0.05f, 0.7f, 0.1f, 1f).getInterpolation(it) }
   }

   NavHost(
      navController = navController,
      startDestination = Screen.Overview,
      enterTransition = {
         slideInHorizontally(
            animationSpec = tween(
               durationMillis = 400,
               easing = emphasizedDeceleratedEasing
            ),
            initialOffsetX = { it }
         ) + fadeIn()
      },
      exitTransition = {
         slideOutHorizontally(
            tween(
               durationMillis = 400,
               easing = emphasizedDeceleratedEasing
            ),
            targetOffsetX = { it }
         ) + fadeOut()
      },
      modifier = Modifier.padding(scaffoldPadding)
   ) {

      overviewScreen(navController = navController)

      createHomeworkScreen(navController = navController)

      createSubjectScreen(navController = navController)

   }
}