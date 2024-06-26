package com.samzuhalsetiawan.habbits.ui.composable.fab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.utils.currentRoute
import com.samzuhalsetiawan.habbits.utils.isRouteOf

@Composable
fun ProvideFloatingActionButton(
   navController: NavHostController,
   navBackStackEntry: NavBackStackEntry?,
   modifier: Modifier = Modifier,
) {
   if (navBackStackEntry.currentRoute isRouteOf Screens.Jurnal::class) {
      FloatingActionButton(onClick = { navController.navigate(Screens.AddHabit) }) {
         Icon(Icons.Filled.Add, "Floating action button.")
      }
   }
}