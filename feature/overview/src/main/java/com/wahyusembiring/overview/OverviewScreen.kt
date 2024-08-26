package com.wahyusembiring.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wahyusembiring.datetime.Moment
import com.wahyusembiring.datetime.formatter.FormattingStyle
import com.wahyusembiring.overview.component.eventcard.EventCard
import com.wahyusembiring.ui.component.tab.PrimaryTab
import com.wahyusembiring.ui.theme.HabitTheme
import com.wahyusembiring.ui.theme.spacing
import java.util.Date
import kotlin.time.Duration.Companion.days


@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    OverviewScreen(
        state = state,
        onUIEvent = { viewModel.onUIEvent(it) }
    )
}

@Suppress("t")
@Composable
private fun OverviewScreen(
    modifier: Modifier = Modifier,
    state: OverviewScreenUIState,
    onUIEvent: (OverviewScreenUIEvent) -> Unit,
) {
    val moment = remember { Moment.now() }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        repeat(7) {
            item {
                val currentMoment = moment + it.days
                val title = when (it) {
                    0 -> stringResource(R.string.today)
                    1 -> stringResource(R.string.tomorrow)
                    else -> currentMoment.day.dayOfWeek
                }
                val date = when (it) {
                    0, 1 -> currentMoment.toString(FormattingStyle.INDO_FULL)
                    else -> currentMoment.toString(FormattingStyle.INDO_MEDIUM)
                }
                val events = when (it) {
                    0 -> state.todayEvents
                    1 -> state.tomorrowEvents
                    2 -> state.next2DaysEvents
                    3 -> state.next3DaysEvents
                    4 -> state.next4DaysEvents
                    5 -> state.next5DaysEvents
                    6 -> state.next6DaysEvents
                    else -> emptyList()
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
                DaySectionHeader(title, date)
                EventCard(
                    modifier = Modifier.padding(
                        horizontal = MaterialTheme.spacing.Large,
                        vertical = MaterialTheme.spacing.Small
                    ),
                    events = events,
                    onEventCheckedChange = { event, isChecked ->
                        if (isChecked) {
                            onUIEvent(OverviewScreenUIEvent.OnMarkEventAsCompleted(event))
                        } else {
                            onUIEvent(OverviewScreenUIEvent.OnMarkEventAsUncompleted(event))
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun DaySectionHeader(
    title: String,
    date: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.Medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OverviewScreenPreview() {
    HabitTheme {
        OverviewScreen(
            state = OverviewScreenUIState(),
            onUIEvent = {}
        )
    }
}
