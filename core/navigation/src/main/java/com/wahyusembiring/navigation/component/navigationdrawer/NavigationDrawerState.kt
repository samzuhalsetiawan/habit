package com.wahyusembiring.navigation.component.navigationdrawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import com.wahyusembiring.navigation.Screen
import com.wahyusembiring.navigation.util.routeSimpleClassName

data class NavigationDrawerState(
    val slideGestureEnabled: Boolean = false,
    val materialDrawerState: DrawerState
)

@Composable
fun rememberNavigationDrawerState(
    navBackStackEntry: NavBackStackEntry?,
    materialDrawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
): NavigationDrawerState {
    val screenWithDrawer = listOf(
        Screen.Overview::class.simpleName,
        Screen.KanbanBoard::class.simpleName
    )
    return remember(navBackStackEntry) {
        NavigationDrawerState(
            slideGestureEnabled = navBackStackEntry?.routeSimpleClassName in screenWithDrawer,
            materialDrawerState = materialDrawerState
        )
    }
}