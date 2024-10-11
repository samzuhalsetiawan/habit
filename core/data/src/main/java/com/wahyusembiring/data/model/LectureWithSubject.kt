package com.wahyusembiring.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.wahyusembiring.data.model.entity.Lecture
import com.wahyusembiring.data.model.entity.Subject

data class LectureWithSubject(
    @Embedded val lecture: Lecture,
    @Relation(
        parentColumn = "id",
        entityColumn = "lecture_id"
    )
    val subjects: List<Subject>
)
