package com.wahyusembiring.ui.component.navigationdrawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class NavigationDrawerState(
   val materialDrawerState: DrawerState
) {
   var slideGestureEnabled: Boolean by mutableStateOf(false)
      private set
}

@Composable
fun rememberNavigationDrawerState(
   materialDrawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
): NavigationDrawerState {
   return remember {
      NavigationDrawerState(
         materialDrawerState = materialDrawerState
      )
   }
}