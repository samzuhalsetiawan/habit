package com.wahyusembiring.habit.domain.utils

object ValidationUtil {

   fun String?.isNotNullOrBlank(): Boolean = this?.isNotBlank() ?: false

}