package com.wahyusembiring.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
   tableName = "homework",
   foreignKeys = [
      ForeignKey(
         entity = Subject::class,
         parentColumns = ["id"],
         childColumns = ["subject"],
         onDelete = ForeignKey.CASCADE,
         onUpdate = ForeignKey.CASCADE
      )
   ]
)
data class Homework(
   @PrimaryKey(autoGenerate = true)
   val id: Int = 0,

   val title: String,

   @ColumnInfo(name = "due_date")
   val dueDate: Date,

   val reminder: Time?,

   val subject: Int,

   val description: String,
) : Event