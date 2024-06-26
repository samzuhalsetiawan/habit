package com.samzuhalsetiawan.habbits.ui.screen.menu_jurnal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.samzuhalsetiawan.habbits.App
import com.samzuhalsetiawan.habbits.MainActivity
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.ui.composable.delete_background.SwipeToDeleteContainer
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalNavigationController
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.utils.isNotNullOrBlank
import com.samzuhalsetiawan.habbits.utils.provideViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuJurnalTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior?,
    modifier: Modifier = Modifier
) {
    val viewModel = provideViewModel { MenuJurnalViewModel(App.repositoryModule.mainRepository) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    MenuJurnalTopAppBar(
        modifier = modifier,
        state = state,
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuJurnalTopAppBar(
    modifier: Modifier = Modifier,
    state: MenuJurnalViewModel.State,
    scrollBehavior: TopAppBarScrollBehavior?
) {

    val currentDate = state.currentDate

    LargeTopAppBar(
        modifier = modifier,
        title = {
            Column {
                Text(
                    text = "Jurnal Saya"
                )
                Text(
                    text = currentDate,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }

        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun MenuJurnal(
    modifier: Modifier = Modifier,
) {
    val menuJurnalViewModel = provideViewModel {
        MenuJurnalViewModel(App.repositoryModule.mainRepository)
    }
    val state by menuJurnalViewModel.state.collectAsStateWithLifecycle()
    val navController = LocalNavigationController.current

    MenuJurnal(
        modifier = modifier,
        listOfHabit = state.habits,
        errorMessage = state.errorMessage,
        onHabitClicked = {
            navController.navigate(Screens.DetailHabit(it))
        },
        onHabitDeleteRequest = {
            menuJurnalViewModel.deleteHabit(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuJurnal(
    modifier: Modifier = Modifier,
    onHabitClicked: (habitId: Int) -> Unit,
    onHabitDeleteRequest: (habitId: Int) -> Unit,
    listOfHabit: List<Habit>,
    errorMessage: String?
) {
    var tabState by remember { mutableIntStateOf(0) }
    val titles = listOf("Semua Habit", "Area Habit 1", "Area Habit 2")

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PrimaryTabRow(selectedTabIndex = tabState) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabState == index,
                        onClick = { tabState = index },
                        text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                    )
                }
            }
            when {
                errorMessage.isNotNullOrBlank() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage.toString()
                        )
                    }
                }
                listOfHabit.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Kamu belum punya habit yang tersimpan"
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            count =  listOfHabit.size,
                            key = { listOfHabit[it].id }
                        ) {
                            SwipeToDeleteContainer(
                                item = listOfHabit[it],
                                onDelete = {
                                    onHabitDeleteRequest(it.id)
                                }
                            ) { habit ->
                                ListItem(
                                    modifier = Modifier.clickable {
                                        onHabitClicked(habit.id)
                                    },
                                    headlineContent = { Text(habit.name) },
                                    trailingContent = {
                                        Box(modifier = Modifier
                                            .background(
                                                color = MaterialTheme.colorScheme.secondaryContainer,
                                                shape = RoundedCornerShape(50)
                                            )
                                            .padding(5.dp, 1.dp)
                                        ) {
                                            Text(
                                                text = "Selesai",
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                        }
                                    }
                                )
                            }
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
                        }
                    }
                }
            }
        }
    }
}