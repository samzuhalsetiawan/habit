package com.samzuhalsetiawan.habbits.ui.screen.menu_register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalNavigationController
import com.samzuhalsetiawan.habbits.ui.screen.Screens

@Composable
fun MenuRegister(
   modifier: Modifier = Modifier,
) {
   val navController = LocalNavigationController.current

   MenuRegister(
      modifier = modifier,
      onLoginHereButtonPressed = {
         navController.popUpAndLunchInSingleTopTo(Screens.Login)
      }
   )
}

@Composable
private fun MenuRegister(
   modifier: Modifier = Modifier,
   onLoginHereButtonPressed: () -> Unit
) {

   Surface(
      modifier = modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background
   ) {
      Box(
         modifier = Modifier.fillMaxSize(),
         contentAlignment = Alignment.Center
      ) {
         ElevatedCard(
            elevation = CardDefaults.cardElevation(
               defaultElevation = 6.dp
            ),
            modifier = Modifier
               .fillMaxSize()
               .padding(16.dp, 24.dp)
         ) {
            Column(
               modifier = Modifier.fillMaxSize(),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally
            ) {
               Text(
                  text = "Buat Akun",
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(16.dp, 24.dp),
                  textAlign = TextAlign.Center,
                  style = MaterialTheme.typography.headlineMedium
               )
               Text(
                  text = "Daftar dengan email dan password atau lewati untuk masuk sebagai Guest",
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(horizontal = 16.dp),
                  textAlign = TextAlign.Center,
                  style = MaterialTheme.typography.bodySmall
               )
               OutlinedTextField(
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(16.dp),
                  value = "",
                  onValueChange = { },
                  label = { Text("Email") }
               )
               OutlinedTextField(
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(horizontal = 16.dp),
                  value = "",
                  onValueChange = { },
                  label = { Text("Password") },
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
               )
               OutlinedTextField(
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(horizontal = 16.dp),
                  value = "",
                  onValueChange = { },
                  label = { Text("Konfirmasi Password") },
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
               )
               Button(
                  modifier = Modifier.padding(vertical = 24.dp),
                  onClick = { },
               ) {
                  Text(text = "Daftar")
               }
               Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically
               ) {
                  Text(
                     text = "Sudah punya akun?",
                     style = MaterialTheme.typography.bodySmall
                  )
                  TextButton(onClick = onLoginHereButtonPressed) {
                     Text(
                        text = "Login disini",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                     )
                  }
               }
            }
         }
         TextButton(
            modifier = Modifier
               .align(Alignment.TopEnd)
               .padding(24.dp),
            onClick = { }) {
            Text(
               text = "Lewati"
            )
         }
      }
   }
}