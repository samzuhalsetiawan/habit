package com.wahyusembiring.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wahyusembiring.common.navigation.Screen
import com.wahyusembiring.datetime.Moment
import com.wahyusembiring.datetime.formatter.FormattingStyle
import com.wahyusembiring.overview.component.eventcard.EventCard
import com.wahyusembiring.ui.component.floatingactionbutton.HomeworkExamAndReminderFAB
import com.wahyusembiring.ui.component.tab.PrimaryTab
import com.wahyusembiring.ui.component.topappbar.TopAppBar
import com.wahyusembiring.ui.theme.HabitTheme
import com.wahyusembiring.ui.theme.spacing
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.time.Duration.Companion.days


@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel,
    navController: NavHostController,
    drawerState: DrawerState
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    OverviewScreen(
        state = state,
        onUIEvent = {
            when (it) {
                is OverviewScreenUIEvent.OnNavigateTo -> {
                    navController.navigate(it.screen)
                }

                is OverviewScreenUIEvent.OnHamburgerMenuClick -> {
                    coroutineScope.launch { drawerState.open() }
                }

                else -> viewModel.onUIEvent(it)
            }
        },
    )
}


@Composable
private fun OverviewScreen(
    modifier: Modifier = Modifier,
    state: OverviewScreenUIState,
    onUIEvent: (OverviewScreenUIEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.overview),
                onMenuClick = { onUIEvent(OverviewScreenUIEvent.OnHamburgerMenuClick) }
            )
        },
        floatingActionButton = {
            HomeworkExamAndReminderFAB(
                onReminderFabClick = { onUIEvent(OverviewScreenUIEvent.OnNavigateTo(Screen.CreateReminder)) },
                onExamFabClick = { onUIEvent(OverviewScreenUIEvent.OnNavigateTo(Screen.CreateExam)) },
                onHomeworkFabClick = { onUIEvent(OverviewScreenUIEvent.OnNavigateTo(Screen.CreateHomework)) }
            )
        }
    ) {
        OverviewScreenMainContent(
            modifier = modifier.padding(it),
            state = state,
            onUIEvent = onUIEvent
        )
    }
}


@Composable
private fun OverviewScreenMainContent(
    modifier: Modifier = Modifier,
    state: OverviewScreenUIState,
    onUIEvent: (OverviewScreenUIEvent) -> Unit,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = state.eventCards,
            key = { it.title.asString(context) }
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
            DaySectionHeader(
                title = it.title.asString(),
                date = it.date.asString()
            )
            EventCard(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.Large,
                    vertical = MaterialTheme.spacing.Small
                ),
                events = it.events,
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
