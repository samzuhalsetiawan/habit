package com.samzuhalsetiawan.habbits.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "starting_question_table")
data class StartingQuestion(

    @PrimaryKey
    @ColumnInfo(name = "question")
    val question: String,

    @ColumnInfo(name = "answer")
    val answer: String,

    @ColumnInfo(name = "score")
    val score: Int
)