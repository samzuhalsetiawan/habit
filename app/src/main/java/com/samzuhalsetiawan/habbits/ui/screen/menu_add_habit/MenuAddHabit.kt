package com.samzuhalsetiawan.habbits.ui.screen.menu_add_habit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samzuhalsetiawan.habbits.App
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.ui.composable.date_picker_range.HabitDatePickerRange
import com.samzuhalsetiawan.habbits.ui.handler.SnackbarHandler
import com.samzuhalsetiawan.habbits.ui.composable.inputfield.DropdownTextField
import com.samzuhalsetiawan.habbits.ui.composable.inputfield.InputTextField
import com.samzuhalsetiawan.habbits.ui.handler.PopUpDialogHandler
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalNavigationController
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalPopUpController
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialogResponse
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.utils.clearFocusOnKeyboardDismiss
import com.samzuhalsetiawan.habbits.utils.provideViewModel
import com.samzuhalsetiawan.habbits.utils.toLocalDateStringFormatted

@Composable
fun MenuAddHabitTopAppBar() {
    val viewModel = provideViewModel {
        MenuAddHabitViewModel(App.repositoryModule.mainRepository)
    }
    val popUpController = LocalPopUpController.current

    val dialog = PopUpDialog.BinaryChoice(
        title = "Simpan Habit",
        message = "Yakin ingin menyimpan habit ini?",
        positiveButtonText = "Iya",
        negaviteButtonText = "Tidak",
        callback = { response ->
            if (response == PopUpDialogResponse.POSITIVE) viewModel.saveHabit()
        }
    )

    MenuAddHabitTopAppBar(
        onSaveButtonPressed = {
            popUpController.show(dialog)
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuAddHabitTopAppBar(
    onSaveButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        },
        title = {
                Text(text = "Buat Habit")
        },
        actions = {
            TextButton(onClick = onSaveButtonPressed) {
                Text(text = "Simpan")
            }
        }
    )
}

@Composable
fun MenuAddHabit() {
    val menuAddHabitViewModel = provideViewModel {
        MenuAddHabitViewModel(App.repositoryModule.mainRepository)
    }
    val state by menuAddHabitViewModel.state.collectAsStateWithLifecycle()
    val navController = LocalNavigationController.current

    SnackbarHandler(
        message = state.snackBarMessage,
        onSnackbarResult = menuAddHabitViewModel::onSnackbarResult
    )

    PopUpDialogHandler(popUpDialog = state.dialog)

    LaunchedEffect(state.navigateToHome) {
        if (state.navigateToHome) navController.popUpAndLunchInSingleTop(Screens.Jurnal)
    }

    MenuAddHabit(
        state = state,
        onSetEverydayReminder = menuAddHabitViewModel::setEveryDayReminder,
        onDatePickerRangeSet = menuAddHabitViewModel::onDatePickerRangeSet,
        onTimePickerConfirm = menuAddHabitViewModel::onTimePickerConfirm,
        onSetIsNoDayLimit = menuAddHabitViewModel::setIsNoDayLimit,
        onSetHabitName = menuAddHabitViewModel::setHabitName,
        onSetHabitType = menuAddHabitViewModel::setHabitType,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuAddHabit(
    state: MenuAddHabitViewModel.State,
    onSetHabitName: (String) -> Unit,
    onSetHabitType: (Habit.Types) -> Unit,
    onSetIsNoDayLimit: (Boolean) -> Unit,
    onSetEverydayReminder: (Boolean) -> Unit,
    onDatePickerRangeSet: (LongRange) -> Unit,
    onTimePickerConfirm: (hour: Int, minute: Int, key: Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowDatePickerRange by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            InputTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clearFocusOnKeyboardDismiss(),
                value = state.habitName,
                onValueChange = onSetHabitName
            )
            Spacer(modifier = Modifier.height(24.dp))
            DropdownTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.habitType.displayName,
                onSetHabitType = onSetHabitType
            )
            Spacer(modifier = Modifier.height(24.dp))
            ElevatedCard{
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Rentang Waktu",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { shouldShowDatePickerRange = true }) {
                            Text(text = if (state.dayStart == 0L) state.currentDate.time.toLocalDateStringFormatted() else state.dayStart.toLocalDateStringFormatted())
                        }
                        IconButton(onClick = { shouldShowDatePickerRange = true }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Pilih tanggal")
                        }
                        TextButton(onClick = { shouldShowDatePickerRange = true }) {
                            Text(text = if (state.dayEnd == 0L) "Pilih Tanggal" else state.dayEnd.toLocalDateStringFormatted())
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(checked = state.isNoDayLimit, onCheckedChange = onSetIsNoDayLimit)
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Tanpa batasan waktu",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            ElevatedCard {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Pengingat",
                        style = MaterialTheme.typography.labelLarge
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(
                            items = state.reminders,
                            key = {
                                it.time
                            },
                            itemContent = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedButton(onClick = {  }) {
                                        Text(text = it.formattedTime)
                                    }
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Hapus reminder",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        )
                        item {
                            TextButton(onClick = { showTimePicker = true }) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah pengingat")
                                Text(text = "Tambahkan Pengingat")
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(checked = state.everydayReminder, onCheckedChange = onSetEverydayReminder)
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Ingatkan saya tiap hari",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
    if (shouldShowDatePickerRange) {
        HabitDatePickerRange(
            onSave = onDatePickerRangeSet,
            onDismiss = { shouldShowDatePickerRange = false }
        )
    }
    if (showTimePicker) {
        AlertDialog(
            title = {
                Text(text = "Pilih waktu", style = MaterialTheme.typography.bodyLarge)
            },
            dismissButton = {
                TextButton(onClick = {
                    showTimePicker = false
                }) {
                    Text(text = "Cancel")
                }
            },
            onDismissRequest = {
                showTimePicker = false
            },
            text = {
                TimePicker(state = timePickerState)
            },
            confirmButton = {
                TextButton(onClick = {
                    onTimePickerConfirm(timePickerState.hour, timePickerState.minute, null)
                    showTimePicker = false
                }) {
                    Text(text = "Ok")
                }
            }
        )
    }
}