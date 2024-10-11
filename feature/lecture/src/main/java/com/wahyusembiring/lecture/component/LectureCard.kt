package com.wahyusembiring.lecture.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wahyusembiring.data.model.LectureWithSubject
import com.wahyusembiring.data.model.entity.Lecture

@Composable
fun LectureCard(
    lectureWithSubjects: LectureWithSubject,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        trailingContent = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
            ) {
                AsyncImage(
                    modifier = Modifier.size(32.dp),
                    model = lectureWithSubjects.lecture.photo,
                    contentDescription = null
                )
            }
        },
        headlineContent = {
            Text(text = lectureWithSubjects.lecture.name)
        },
        supportingContent = {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = lectureWithSubjects.subjects.joinToString { it.name }
            )
        }
    )
}