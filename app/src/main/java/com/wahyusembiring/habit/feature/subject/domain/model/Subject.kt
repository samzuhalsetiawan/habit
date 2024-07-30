package com.wahyusembiring.habit.feature.subject.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.wahyusembiring.habit.feature.lecture.domain.model.Lecture

@Entity(
   tableName = "subject",
   foreignKeys = [
      ForeignKey(
         entity = Lecture::class,
         parentColumns = ["id"],
         childColumns = ["lecture"],
         onDelete = ForeignKey.CASCADE,
         onUpdate = ForeignKey.CASCADE
      )
   ]
)
data class Subject(
   @PrimaryKey(autoGenerate = true)
   val id: Int = 0,

   val name: String,

   val color: Color,

   val room: String,

   val lecture: Int?,

   val description: String,
)