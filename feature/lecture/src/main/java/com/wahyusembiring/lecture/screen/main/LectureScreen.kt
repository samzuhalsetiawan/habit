package com.wahyusembiring.lecture.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.wahyusembiring.lecture.R
import com.wahyusembiring.lecture.component.LectureCard
import com.wahyusembiring.ui.component.topappbar.TopAppBar

@Composable
fun LectureScreen(
    viewModel: LectureScreenViewModel,
    navController: NavController,
    drawerState: DrawerState,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.showDrawer) {
        if (state.showDrawer) drawerState.open()
    }

    LectureScreen(
        navController = navController,
        state = state,
        onUIEvent = viewModel::onUIEvent
    )

}

@Composable
private fun LectureScreen(
    navController: NavController,
    state: LectureScreenUIState,
    onUIEvent: (LectureScreenUIEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.lectures),
                onMenuClick = {
                    onUIEvent(LectureScreenUIEvent.OnHamburgerMenuClick)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onUIEvent(LectureScreenUIEvent.OnAddLectureClick(navController))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            items(state.listOfLecturerWithSubjects.size) { index ->
                LectureCard(
                    lecturerWithSubjects = state.listOfLecturerWithSubjects[index],
                    onClick = { /*TODO*/ }
                )
                if (index != state.listOfLecturerWithSubjects.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}