package com.samzuhalsetiawan.habbits.ui.screen.menu_pengaturan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MenuPengaturan(
   modifier: Modifier = Modifier
) {
   Column(
      modifier = modifier.fillMaxSize()
   ) {
      Text(text = "Hello Pengaturan")
   }
}