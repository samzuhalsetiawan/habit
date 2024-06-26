package com.samzuhalsetiawan.habbits.ui.screen

import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object Welcome : Screens

    @Serializable
    data object Pertanyaan : Screens

    @Serializable
    data object Result : Screens

    @Serializable
    data object Login : Screens

    @Serializable
    data object Jurnal : Screens

    @Serializable
    data object Progress : Screens

    @Serializable
    data object Pengaturan : Screens

    @Serializable
    data object Register : Screens

    @Serializable
    data object AddHabit : Screens

    @Serializable
    data class DetailHabit(val habitId: Int) : Screens

}