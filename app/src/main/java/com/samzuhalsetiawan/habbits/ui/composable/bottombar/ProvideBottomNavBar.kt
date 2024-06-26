package com.samzuhalsetiawan.habbits.ui.composable.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.samzuhalsetiawan.habbits.models.BottomNavigationItem
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.utils.currentRoute
import com.samzuhalsetiawan.habbits.utils.isRouteOf
import kotlinx.coroutines.flow.map

@Composable
fun ProvideBottomNavBar(
   navController: NavHostController,
   navBackStackEntry: NavBackStackEntry?
) {
   val shouldShowBottomNavBar =
      BottomNavigationItem.bottomNavigationItems.any { navBackStackEntry.currentRoute isRouteOf it.route::class }

   if (shouldShowBottomNavBar) {
      BottomNavBar(
         selected = { item ->  navBackStackEntry.currentRoute isRouteOf item.route::class },
         onClick = { item ->
            navController.navigate(item.route) {
               popUpTo(Screens.Jurnal) {
                  saveState = true
               }
               launchSingleTop = true
               restoreState = true
            }
         }
      )
   }

}

@Composable
private fun BottomNavBar(
   bottomNavigationItems: List<BottomNavigationItem> = BottomNavigationItem.bottomNavigationItems,
   selected: (item: BottomNavigationItem) -> Boolean,
   onClick: (item: BottomNavigationItem) -> Unit
) {
   NavigationBar {
      bottomNavigationItems.forEach { item ->
         NavigationBarItem(
            icon = { Icon(item.icon, contentDescription = item.label) },
            label = { Text(item.label) },
            selected = selected(item),
            onClick = { onClick(item) }
         )
      }
   }
}