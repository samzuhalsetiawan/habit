package com.wahyusembiring.kanban

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun KanbanBoardScreen(
    viewModel: KanbanBoardScreenViewModel
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    KanbanBoardScreen(
        state = state,
        onUIEvent = viewModel::onUIEvent
    )

}

@Composable
private fun KanbanBoardScreen(
    state: KanbanBoardScreenUIState,
    onUIEvent: (KanbanBoardScreenUIEvent) -> Unit
) {

    
}