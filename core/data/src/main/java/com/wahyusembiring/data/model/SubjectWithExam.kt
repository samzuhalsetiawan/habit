package com.wahyusembiring.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.wahyusembiring.data.model.entity.Exam
import com.wahyusembiring.data.model.entity.Subject

data class SubjectWithExam(
    @Embedded val subject: Subject,
    @Relation(
        parentColumn = "id",
        entityColumn = "subject_id",
    )
    val exams: List<Exam>
)