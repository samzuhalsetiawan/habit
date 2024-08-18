package com.wahyusembiring.ui.component.modalbottomsheet.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wahyusembiring.ui.R
import com.wahyusembiring.ui.theme.spacing

@Composable
fun CloseAndSaveHeader(
   modifier: Modifier = Modifier,
   onCloseButtonClicked: () -> Unit,
   onSaveButtonClicked: () -> Unit,
   closeButtonDescription: String,
   colors: CloseAndSaveHeaderColors = CloseAndSaveHeaderDefaults.colors()
) {
   Row(
      modifier = modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
   ) {
      IconButton(onClick = onCloseButtonClicked) {
         Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = closeButtonDescription,
            tint = colors.closeButtonColor
         )
      }
      Button(
         modifier = Modifier.padding(end = MaterialTheme.spacing.Medium),
         colors = colors.saveButtonColor,
         onClick = onSaveButtonClicked
      ) {
         Text(text = stringResource(R.string.save))
      }
   }
}

object CloseAndSaveHeaderDefaults {
   @Composable
   fun colors(
      closeButtonColor: Color = LocalContentColor.current,
      saveButtonContainerColor: Color = ButtonDefaults.buttonColors().containerColor,
      saveButtonContentColor: Color = ButtonDefaults.buttonColors().contentColor,
   ): CloseAndSaveHeaderColors {
      val materialSaveButtonColor = ButtonDefaults.buttonColors(
         containerColor = saveButtonContainerColor,
         contentColor = saveButtonContentColor
      )
      return CloseAndSaveHeaderColors(
         closeButtonColor = closeButtonColor,
         saveButtonColor = materialSaveButtonColor,
      )
   }
}

data class CloseAndSaveHeaderColors(
   val closeButtonColor: Color,
   val saveButtonColor: ButtonColors,
)