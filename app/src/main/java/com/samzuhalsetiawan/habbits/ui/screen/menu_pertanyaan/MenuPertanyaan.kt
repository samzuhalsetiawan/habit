package com.samzuhalsetiawan.habbits.ui.screen.menu_pertanyaan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.samzuhalsetiawan.habbits.MainViewModel
import com.samzuhalsetiawan.habbits.R
import com.samzuhalsetiawan.habbits.models.OpsiJawaban
import com.samzuhalsetiawan.habbits.models.Pertanyaan
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalNavigationController
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalPopUpController
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.PopUpDialog
import com.samzuhalsetiawan.habbits.ui.screen.Screens

@Composable
fun MenuPertanyaan(
    modifier: Modifier = Modifier,
) {
    val menuPertanyaanViewModel = viewModel<MenuPertanyaanViewModel>()
    val menuPertanyaanState by menuPertanyaanViewModel.state.collectAsStateWithLifecycle()
    val popUpController = LocalPopUpController.current
    val navController = LocalNavigationController.current

    MenuPertanyaan(
        modifier = modifier,
        state = menuPertanyaanState,
        onSetSelectedOption = menuPertanyaanViewModel::setSelectedOption,
        onBackButtonClicked = {
            menuPertanyaanViewModel.onBackButtonClicked {
                navController.popBackStack()
            }
        },
        onConfirmButtonClicked = menuPertanyaanViewModel::onConfirmButtonClicked,
        onNavigateToResult = { navController.navigate(Screens.Result) },
        onShowConfirmationDialog = { title, message ->
            popUpController.show {
                PopUpDialog.Confirmation(
                    title = title,
                    message = message,
                    acknowledgeButtonText = "Ok"
                )
            }
        }
    )
}

@Composable
private fun MenuPertanyaan(
    state: MenuPertanyaanViewModel.State,
    onSetSelectedOption: (OpsiJawaban) -> Unit,
    onBackButtonClicked: () -> Unit,
    onConfirmButtonClicked: (pertanyaan: Pertanyaan, jawaban: String?, onNavigateToResult: () -> Unit, onQuestionNotAnswered: () -> Unit) -> Unit,
    onNavigateToResult: () -> Unit,
    onShowConfirmationDialog: (title: String, message: String) -> Unit,
    modifier: Modifier = Modifier
) {

    val currentQuestion = state.currentQuestion
    val selectedOption = state.selectedOption

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AlertDialog(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_question_mark),
                    contentDescription = "Example Icon"
                )
            },
            title = {
                Text(text = currentQuestion.question, textAlign = TextAlign.Center)
            },
            text = {
                Column(Modifier.selectableGroup()) {
                    currentQuestion.answers.forEachIndexed { index, opsi ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (opsi.jawaban == selectedOption?.jawaban),
                                    onClick = { onSetSelectedOption(opsi) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp)
                        ) {
                            RadioButton(
                                selected = (opsi.jawaban == selectedOption?.jawaban),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = opsi.jawaban,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        if (index != currentQuestion.answers.lastIndex) {
                            Spacer(modifier = Modifier.height(14.dp))
                        }
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = onBackButtonClicked) {
                    Text(text = "Kembali")
                }            
            },
            onDismissRequest = onBackButtonClicked,
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmButtonClicked(
                            currentQuestion,
                            selectedOption?.jawaban,
                            onNavigateToResult
                        ) {
                            onShowConfirmationDialog(
                                "Oops!",
                                "Mohon Jawab pertanyaan ini terlebih dahulu"
                            )
                        }
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}