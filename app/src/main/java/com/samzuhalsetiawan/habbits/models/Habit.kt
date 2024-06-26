package com.samzuhalsetiawan.habbits.models

class Habit(
    val id: Int  = 0,
    val name: String = "",
    val isGoodHabit: Boolean = true,
    val dayStart: Long = 0L,
    val dayEnd: Long? = 0L,
    val isEndless: Boolean = false,
    val repeatEveryday: Boolean = false,
    val types: Types = Types.HEALTH
) {
    enum class Types(val displayName: String) {
        HEALTH("Health"),
        PRODUCTIVITY("Productivity"),
        SELF_DEVELOPMENT("Self Development")
    }
}