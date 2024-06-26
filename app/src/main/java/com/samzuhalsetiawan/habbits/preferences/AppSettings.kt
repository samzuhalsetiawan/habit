package com.samzuhalsetiawan.habbits.preferences

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val isNewUser: Boolean = true,
    val isLogin: Boolean = false
)