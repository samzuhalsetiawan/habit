package com.wahyusembiring.grades

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.imageLoader
import com.wahyusembiring.common.navigation.Screen
import com.wahyusembiring.data.model.entity.Exam
import com.wahyusembiring.data.model.entity.Homework
import com.wahyusembiring.grades.component.ExamCard
import com.wahyusembiring.grades.component.AssigntmentPicker
import com.wahyusembiring.grades.component.HomeworkCard
import com.wahyusembiring.ui.component.scoredialog.ScoreDialog
import com.wahyusembiring.ui.component.topappbar.TopAppBar
import com.wahyusembiring.ui.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun GradesScreen(
    viewModel: GradesScreenViewModel,
    navController: NavController,
    drawerState: DrawerState
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    GradesScreen(
        state = state,
        onUIEvent = { event ->
            when (event) {
                is GradesScreenUIEvent.OnHamburgerMenuClick -> {
                    coroutineScope.launch { drawerState.open() }
                }

                is GradesScreenUIEvent.OnExamClick -> {
                    navController.navigate(Screen.CreateExam(event.exam.id))
                }

                else -> viewModel.onUIEvent(event)
            }
        }
    )
}

@Composable
@Suppress("t")
private fun GradesScreen(
    state: GradesScreenUIState,
    onUIEvent: (event: GradesScreenUIEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    var showAssignmentPicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.grades),
                onMenuClick = { onUIEvent(GradesScreenUIEvent.OnHamburgerMenuClick) }
            )
        },
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical
                )
        ) {
            for (subject in state.subjects) {
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.spacing.Medium,
                            vertical = MaterialTheme.spacing.Small
                        ),
                    text = subject.subject.name,
                    style = MaterialTheme.typography.titleMedium
                )
                HorizontalDivider()
                for (exam in subject.exams) {
                    ExamCard(
                        subject = subject.subject,
                        exam = exam,
                        date = exam.date,
                        onClick = {
                            onUIEvent(GradesScreenUIEvent.OnExamClick(exam))
                        }
                    )
                }
                for (homework in subject.homeworks) {
                    HomeworkCard(
                        subject = subject.subject,
                        homework = homework,
                        date = homework.dueDate,
                        onClick = {
                            onUIEvent(GradesScreenUIEvent.OnHomeworkClick(homework))
                        }
                    )
                }
                if (subject.exams.isEmpty() && subject.homeworks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(R.string.no_graded_assignment_for_this_subject))
                    }
                }
            }
            if (state.subjects.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            modifier = Modifier.width(64.dp),
                            model = R.drawable.no_data_picture,
                            contentDescription = null,
                            imageLoader = context.imageLoader
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
                        Text(
                            text = stringResource(id = R.string.you_don_t_have_any_subject),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

