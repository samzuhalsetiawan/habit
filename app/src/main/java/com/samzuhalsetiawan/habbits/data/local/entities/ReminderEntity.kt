package com.samzuhalsetiawan.habbits.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
   tableName = "reminder_table",
   foreignKeys = [
      ForeignKey(
         entity = HabitEntity::class,
         parentColumns = ["id"],
         childColumns = ["habit_id"],
         onDelete = ForeignKey.CASCADE,
         onUpdate = ForeignKey.CASCADE,
      )
   ]
)
data class ReminderEntity(
   @PrimaryKey(autoGenerate = true)
   val id: Int = 0,

   @ColumnInfo(name = "habit_id")
   val habitId: Int,

   val hour: Int,

   val minute: Int,
)
