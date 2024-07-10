package com.wahyusembiring.habit.presentation.composable.topappbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.domain.utils.GetterUtil.routeName
import com.wahyusembiring.habit.presentation.screen.Screen

@Composable
fun TopAppBar(
   navBackStackEntry: NavBackStackEntry?,
   onMenuClick: () -> Unit
) {
   if (navBackStackEntry == null) return
   val screenName = when (navBackStackEntry.routeName) {
      Screen.Home::class.simpleName -> stringResource(R.string.home)
      else -> return
   }
   val state = TopAppBarState(
      title = screenName
   )
   val userAction = object : TopAppBarUserAction {
      override fun onMenuClick() = onMenuClick()
   }

   TopAppBar(
      state = state,
      userAction = userAction
   )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
   state: TopAppBarState,
   userAction: TopAppBarUserAction
) {
   androidx.compose.material3.TopAppBar(
      title = {
         Text(text = state.title)
      },
      navigationIcon = {
         IconButton(onClick = userAction::onMenuClick) {
            Icon(
               painter = painterResource(id = R.drawable.ic_hamburger_menu),
               contentDescription = stringResource(R.string.menu)
            )
         }
      },
   )
}

data class TopAppBarState(
   val title: String
)

interface TopAppBarUserAction {
   fun onMenuClick()
}

@Preview(showBackground = true)
@Composable
private fun TopAppBarPreview() {
   val state = TopAppBarState(
      title = "Home"
   )
   val userAction = object : TopAppBarUserAction {
      override fun onMenuClick() {

      }
   }

   TopAppBar(
      state = state,
      userAction = userAction
   )
}