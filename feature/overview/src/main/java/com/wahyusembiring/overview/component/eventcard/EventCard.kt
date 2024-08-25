package com.wahyusembiring.overview.component.eventcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wahyusembiring.data.model.Exam
import com.wahyusembiring.data.model.Homework
import com.wahyusembiring.data.model.Reminder
import com.wahyusembiring.overview.R
import com.wahyusembiring.overview.model.Event
import com.wahyusembiring.ui.theme.spacing

@Composable
fun EventCard(
    modifier: Modifier = Modifier,
    events: List<Event> = emptyList(),
    onEventCheckedChange: (event: Event, isChecked: Boolean) -> Unit = { _, _ -> }
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Header()
        if (events.isEmpty()) {
            NoEventBody()
        } else {
            Body(
                events = events,
                onEventCheckedChange = onEventCheckedChange
            )
        }
        Footer()
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.Medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.ic_event),
                contentDescription = stringResource(R.string.event_icon),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
        Text(
            text = stringResource(R.string.event_overview),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun Body(
    events: List<Event>,
    onEventCheckedChange: (event: Event, isChecked: Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.Medium),
    ) {
        for (event in events) {
            BodyEventList(
                isChecked = event.isCompleted,
                onCheckedChange = { onEventCheckedChange(event, it) },
                title = event.title,
                subjectColor = event.subject.color,
                subjectName = event.subject.name,
                eventType = event.eventType.displayName.asString()
            )
        }
    }
}

@Composable
private fun BodyEventList(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    title: String,
    subjectColor: Color,
    subjectName: String,
    eventType: String
) {
    var checkBoxWidth by remember { mutableIntStateOf(0) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = isChecked,
                    onValueChange = onCheckedChange,
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                modifier = Modifier.onSizeChanged { checkBoxWidth = it.width },
                checked = isChecked,
                onCheckedChange = null
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.let {
                    if (!isChecked) it else it.copy(
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = checkBoxWidth.dp / 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        color = subjectColor,
                        shape = RoundedCornerShape(50)
                    )
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
            Text(
                text = subjectName,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
            Text(
                text = "($eventType)",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun NoEventBody() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.Medium),
    ) {
        Image(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.Medium),
            painter = painterResource(id = R.drawable.relaxing),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
        Text(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.Small),
            text = "There are no events",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "You can add new events with the button in the bottom right corner",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun Footer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.Medium),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_read_more),
                contentDescription = stringResource(R.string.show_more)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
            Text(text = stringResource(R.string.show_more))
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
        Button(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = stringResource(R.string.add_new_event)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
            Text(text = stringResource(R.string.new_event))
        }
    }
}

@Preview
@Composable
private fun EventCardPreview() {
    EventCard()
}