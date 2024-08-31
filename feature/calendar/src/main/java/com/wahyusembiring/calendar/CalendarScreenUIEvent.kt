package com.wahyusembiring.calendar

sealed class CalendarScreenUIEvent {
    data object OnHamburgerMenuClick : CalendarScreenUIEvent()
}