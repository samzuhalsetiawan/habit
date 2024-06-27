package com.samzuhalsetiawan.habbits.ui.screen.menu_add_habit

import com.samzuhalsetiawan.habbits.models.Habit

interface MenuAddHabitAction {
   fun onSetHabitName(name: String)
   fun onSetHabitType(type: Habit.Types)
   fun onSetIsNoDayLimit(noLimit: Boolean)
   fun onSetEverydayReminder(everyday: Boolean)
   fun onDatePickerRangeSet(range: LongRange)
   fun onTimePickerConfirm(hour: Int, minute: Int, key: Int? = null)
}