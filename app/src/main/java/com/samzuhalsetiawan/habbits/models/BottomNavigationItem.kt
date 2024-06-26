package com.samzuhalsetiawan.habbits.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.samzuhalsetiawan.habbits.ui.screen.Screens

data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: Screens
) {
    companion object {
        val bottomNavigationItems: List<BottomNavigationItem> =
            listOf(
                BottomNavigationItem(
                    label = "Jurnal",
                    icon = Icons.Filled.Home,
                    route = Screens.Jurnal
                ),
                BottomNavigationItem(
                    label = "Progress",
                    icon = Icons.Filled.DateRange,
                    route = Screens.Progress
                ),
                BottomNavigationItem(
                    label = "Pengaturan",
                    icon = Icons.Filled.Settings,
                    route = Screens.Pengaturan
                )
            )
    }
}
