package com.samzuhalsetiawan.habbits.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("habit_table")
data class HabitEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    @ColumnInfo("habit_type")
    val habitType: String,

    @ColumnInfo("start_from")
    val startFrom: Long,

    @ColumnInfo("end_until")
    val endUntil: Long,

    @ColumnInfo("is_endless")
    val isEndless: Boolean,

    @ColumnInfo("repeat_everyday")
    val repeatEveryday: Boolean,

    val streak: Int = 0
)
