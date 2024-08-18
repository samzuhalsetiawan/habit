package com.wahyusembiring.navigation

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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MainNavigation(
   navController: NavHostController,
   scaffoldPadding: PaddingValues,
   builder: NavGraphBuilder.() -> Unit
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
      modifier = Modifier.padding(scaffoldPadding),
      builder = builder
   )
}