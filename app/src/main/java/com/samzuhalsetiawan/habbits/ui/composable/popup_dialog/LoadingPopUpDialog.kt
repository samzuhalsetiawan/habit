package com.samzuhalsetiawan.habbits.ui.composable.popup_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.habbits.ui.theme.HabbitsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingPopUpDialog(
   modifier: Modifier = Modifier,
) {
   BasicAlertDialog(
      modifier = modifier
         .background(
            color = AlertDialogDefaults.containerColor,
            shape = RoundedCornerShape(18)
         ),
      onDismissRequest = {  },
   ) {
      Column(
         modifier = Modifier.padding(vertical = 28.dp),
         verticalArrangement = Arrangement.SpaceBetween,
         horizontalAlignment = Alignment.CenterHorizontally
      ) {
         Text(
            text = "Menyimpan...",
            style = MaterialTheme.typography.titleMedium
         )
         Spacer(modifier = Modifier.height(16.dp))
         CircularProgressIndicator()
      }
   }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPopUpDialogPreview() {
   HabbitsTheme {
      Surface(
         modifier = Modifier.fillMaxSize()
      ) {
         LoadingPopUpDialog()
      }
   }
}