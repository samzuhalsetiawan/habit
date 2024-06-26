package com.samzuhalsetiawan.habbits.ui.screen.menu_result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.samzuhalsetiawan.habbits.App
import com.samzuhalsetiawan.habbits.R
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalNavigationController
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.utils.provideViewModel

@Composable
fun MenuResult(
    modifier: Modifier = Modifier
) {

    val viewModel = provideViewModel {
        MenuResultViewModel(App.repositoryModule.mainRepository)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = LocalNavigationController.current

    MenuResult(
        state = state,
        onConfirmButtonPressed = {
            viewModel.onConfirmButtonClicked()
            navController.navigate(Screens.Login)
        },
        modifier = modifier
    )
}

@Composable
private fun MenuResult(
    state: MenuResultViewModel.State,
    onConfirmButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    val results = state.results

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AlertDialog(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sparkle),
                    contentDescription = "Example Icon"
                )
            },
            title = {
                Text(text = "Kesimpulan Habits Kamu", textAlign = TextAlign.Center)
            },
            text = {
                   Column {
                       Text(text = "Health")
                       Spacer(modifier = Modifier.height(6.dp))
                       LinearProgressIndicator(
                           modifier = Modifier.fillMaxWidth(),
                           progress = { results[Habit.Types.HEALTH] ?: 0f }
                       )
                       Spacer(modifier = Modifier.height(16.dp))
                       Text(text = "Productivity")
                       Spacer(modifier = Modifier.height(6.dp))
                       LinearProgressIndicator(
                           modifier = Modifier.fillMaxWidth(),
                           progress = { results[Habit.Types.PRODUCTIVITY] ?: 0f }
                       )
                       Spacer(modifier = Modifier.height(16.dp))
                       Text(text = "Self Development")
                       Spacer(modifier = Modifier.height(6.dp))
                       LinearProgressIndicator(
                           modifier = Modifier.fillMaxWidth(),
                           progress = { results[Habit.Types.SELF_DEVELOPMENT] ?: 0f }
                       )
                   }
            },
            onDismissRequest = {

            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonPressed
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {

            }
        )

    }
}