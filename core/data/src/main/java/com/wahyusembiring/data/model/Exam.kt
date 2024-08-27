package com.wahyusembiring.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "exam",
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
data class Exam(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val date: Date,

    val reminder: Time?,

    val subject: Int,

    val category: ExamCategory,

    val completed: Boolean = false,

    val description: String,
) : Event

enum class ExamCategory {
    WRITTEN, ORAL, PRACTICAL
}