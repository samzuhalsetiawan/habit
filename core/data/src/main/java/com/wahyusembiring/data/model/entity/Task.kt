package com.wahyusembiring.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "task",
    foreignKeys = [
        ForeignKey(
            entity = Thesis::class,
            parentColumns = ["id"],
            childColumns = ["thesis_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "thesis_id")
    val thesisId: Int,

    val name: String,

    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,

    @ColumnInfo(name = "due_date")
    val dueDate: Date
)