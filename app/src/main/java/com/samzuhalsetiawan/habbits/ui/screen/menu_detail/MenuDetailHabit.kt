package com.samzuhalsetiawan.habbits.ui.screen.menu_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import com.samzuhalsetiawan.habbits.App
import com.samzuhalsetiawan.habbits.R
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.models.HabitHistory
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.ui.composable.detail_card.DetailCard
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.ui.theme.HabbitsTheme
import com.samzuhalsetiawan.habbits.utils.provideViewModel

@Composable
fun MenuDetailHabit(
    habitId: Int,
) {
    val menuDetailHabitViewModel = provideViewModel {
        MenuDetailViewModel(App.repositoryModule.mainRepository, habitId)
    }
    val state by menuDetailHabitViewModel.state.collectAsStateWithLifecycle()
    MenuDetailHabit(
        state = state,
        onActionButtonPressed = { habitId, action ->
            menuDetailHabitViewModel.markHabitStatus(habitId, action)
        }
    )
}

@Composable
private fun MenuDetailHabit(
    state: MenuDetailViewModel.MenuDetailHabitState,
    modifier: Modifier = Modifier,
    onActionButtonPressed: (habitId: Int, action: HabitHistory.Status) -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            DetailCard(
                habit = state.habit,
                histories = state.histories,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                FloatingActionButton(
                    contentColor = MaterialTheme.colorScheme.background,
                    containerColor = colorResource(id = R.color.customRed),
                    onClick = {
                        onActionButtonPressed(
                            state.habit.id,
                            HabitHistory.Status.FAILED
                        )
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Failed"
                    )
                }
                FloatingActionButton(
                    modifier = Modifier
                        .size(90.dp)
                        .padding(8.dp),
                    contentColor = MaterialTheme.colorScheme.background,
                    containerColor = colorResource(id = R.color.customGreen),
                    onClick = {
                        onActionButtonPressed(
                            state.habit.id,
                            HabitHistory.Status.COMPLETE
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Done"
                    )
                }
                FloatingActionButton(
                    contentColor = MaterialTheme.colorScheme.background,
                    containerColor = colorResource(id = R.color.customYellow),
                    onClick = {
                        onActionButtonPressed(
                            state.habit.id,
                            HabitHistory.Status.SKIPPED
                        )
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_skip),
                        contentDescription = "Done"
                    )
                }
            }
        }
    }
}