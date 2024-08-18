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
import com.wahyusembiring.ui.component.popup.PopUpHandler
import com.wahyusembiring.ui.theme.spacing


@Composable
fun CreateSubjectScreen(
   onNavigateBack: () -> Unit,
   viewModel: CreateSubjectViewModel,
) {
   val state by viewModel.state.collectAsStateWithLifecycle()
   val context = LocalContext.current

   CreateSubjectScreen(
      modifier = Modifier.fillMaxSize(),
      state = state,
      onUIEvent = { viewModel.onUIEvent(it) },
      onNavigateBack = onNavigateBack
   )
}

@Composable
private fun CreateSubjectScreen(
   modifier: Modifier = Modifier,
   state: CreateSubjectScreenUIState,
   onUIEvent: (CreateSubjectScreenUIEvent) -> Unit,
   onNavigateBack: () -> Unit,
) {
   Column(
      modifier = modifier
   ) {
      BackAndSaveHeader(
         onBackButtonClicked = { onNavigateBack() },
         onSaveButtonClicked = { onUIEvent(CreateSubjectScreenUIEvent.OnSaveButtonClicked) }
      )
      OutlinedTextField(
         modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.Medium),
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
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.Medium),
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

   PopUpHandler(
      popUps = state.popUps,
      initialColorForColorPicker = state.color
   )
}


@Composable
private fun ChooseColorButton(
   color: Color,
   onClick: () -> Unit
) {
   ListItem(
      modifier = Modifier
         .clickable(onClick = onClick)
         .padding(horizontal = MaterialTheme.spacing.Medium),
      colors = ListItemDefaults.colors(containerColor = Color.Transparent),
      leadingContent = {
         Box(
            modifier = Modifier
               .size(MaterialTheme.spacing.Medium)
               .background(
                  color = color,
                  shape = RoundedCornerShape(50)
               )
               .clip(RoundedCornerShape(50))
               .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(50))
         )
      },
      headlineContent = {
         Text(
            text = stringResource(R.string.color),
            color = color,
            style = TextStyle(
               shadow = Shadow(
                  color = Color.Black,
                  offset = Offset(2f, 2f),
                  blurRadius = 4f
               )
            )
         )
      }
   )
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
      onNavigateBack = {}
   )
}