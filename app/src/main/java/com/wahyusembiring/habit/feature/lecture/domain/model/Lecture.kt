package com.wahyusembiring.habit.feature.lecture.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
   tableName = "lecture"
)
data class Lecture(
   @PrimaryKey(autoGenerate = true)
   val id: Int = 0,

   val photo: String,

   val name: String,

   val phone: List<String>,

   val email: List<String>,

   val address: List<String>,

   val officeHour: List<OfficeHour>,

   val website: List<String>,
)