package com.wahyusembiring.datetime

import com.wahyusembiring.datetime.formatter.Formatter
import com.wahyusembiring.datetime.formatter.FormattingStyle
import com.wahyusembiring.datetime.formatter.java.JavaPatternSymbol.DAY_OF_MONTH
import com.wahyusembiring.datetime.formatter.java.JavaPatternSymbol.DAY_OF_MONTH_WITH_ZERO
import com.wahyusembiring.datetime.formatter.java.JavaPatternSymbol.DAY_OF_WEEK
import com.wahyusembiring.datetime.formatter.java.JavaPatternSymbol.DAY_OF_WEEK_SHORT
import com.wahyusembiring.datetime.formatter.java.JavaPatternSymbol.MONTH_NAME
import com.wahyusembiring.datetime.formatter.java.JavaPatternSymbol.MONTH_NAME_SHORT
import com.wahyusembiring.datetime.formatter.java.JavaPatternSymbol.YEAR
import com.wahyusembiring.datetime.formatter.java.JavaPatternSymbol.YEAR_SHORT
import com.wahyusembiring.datetime.formatter.java.JavaFormatter
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

class Moment private constructor(
    private val instant: Instant = Clock.System.now(),
    private val formatter: Formatter = JavaFormatter()
) {

    private val localDateTime: LocalDateTime =
        instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val hour: Int = localDateTime.hour

    val minute: Int = localDateTime.minute

    operator fun plus(duration: Duration): Moment {
        return by(instant = instant + duration)
    }

    operator fun minus(duration: Duration): Moment {
        return by(instant = instant - duration)
    }

    fun toString(pattern: String): String {
        return formatter.format(instant, pattern)
    }

    fun toString(formattingStyle: FormattingStyle): String {
        return formatter.format(instant, formattingStyle)
    }

    override fun toString(): String {
        return instant.toString()
    }

    companion object {
        fun now(): Moment {
            return Moment()
        }

        fun by(instant: Instant): Moment {
            return Moment(instant)
        }

        fun fromEpochMilliseconds(epochMilliseconds: Long): Moment {
            return Moment(Instant.fromEpochMilliseconds(epochMilliseconds))
        }
    }

}