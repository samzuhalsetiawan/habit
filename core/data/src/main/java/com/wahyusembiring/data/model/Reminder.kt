package com.wahyusembiring.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "reminder")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val date: Date,

    val time: Time,

    val color: Color,

    val completed: Boolean = false,

    val description: String,
) : Event