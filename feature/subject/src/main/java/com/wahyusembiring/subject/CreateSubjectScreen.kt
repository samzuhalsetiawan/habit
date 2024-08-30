package com.wahyusembiring.subject

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wahyusembiring.ui.component.button.ChooseColorButton
import com.wahyusembiring.ui.component.popup.PopUpHandler
import com.wahyusembiring.ui.theme.spacing


@Composable
fun CreateSubjectScreen(
    viewModel: CreateSubjectViewModel,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CreateSubjectScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onUIEvent = {
            when (it) {
                is CreateSubjectScreenUIEvent.OnNavigateBack -> navController.popBackStack()
                else -> viewModel.onUIEvent(it)
            }
        }
    )
}

@Composable
private fun CreateSubjectScreen(
    modifier: Modifier = Modifier,
    state: CreateSubjectScreenUIState,
    onUIEvent: (CreateSubjectScreenUIEvent) -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            BackAndSaveHeader(
                onBackButtonClicked = { onUIEvent(CreateSubjectScreenUIEvent.OnNavigateBack) },
                onSaveButtonClicked = { onUIEvent(CreateSubjectScreenUIEvent.OnSaveButtonClicked) }
            )
            Column(
                modifier = Modifier
                   .fillMaxSize()
                   .padding(MaterialTheme.spacing.Medium)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = stringResource(R.string.subject_name))
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = com.wahyusembiring.ui.R.drawable.ic_title),
//               painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = stringResource(R.string.subject_name),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    singleLine = true,
                    value = state.name,
                    onValueChange = { onUIEvent(CreateSubjectScreenUIEvent.OnSubjectNameChanged(it)) },
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = stringResource(R.string.room),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.room))
                    },
                    singleLine = true,
                    value = state.room,
                    onValueChange = { onUIEvent(CreateSubjectScreenUIEvent.OnRoomChanged(it)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
                ChooseColorButton(
                    color = state.color,
                    onClick = { onUIEvent(CreateSubjectScreenUIEvent.OnPickColorButtonClicked) }
                )
            }
        }

        PopUpHandler(
            popUps = state.popUps,
            initialColorForColorPicker = state.color
        )
    }
}

@Composable
private fun BackAndSaveHeader(
    onBackButtonClicked: () -> Unit,
    onSaveButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackButtonClicked
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back_arrow),
                contentDescription = stringResource(R.string.back)
            )
        }
        Button(
            modifier = Modifier.padding(end = MaterialTheme.spacing.Medium),
            onClick = onSaveButtonClicked
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateSubjectScreenPreview() {
    CreateSubjectScreen(
        state = CreateSubjectScreenUIState(),
        onUIEvent = {},
    )
}