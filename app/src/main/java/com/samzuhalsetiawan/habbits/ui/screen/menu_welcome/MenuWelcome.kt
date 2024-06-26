package com.samzuhalsetiawan.habbits.ui.screen.menu_welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalNavigationController
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.utils.provideViewModel


@Composable
fun MenuWelcome(
    modifier: Modifier = Modifier
) {

    val viewModel: MenuWelcomeViewModel = provideViewModel()
    val navController = LocalNavigationController.current

    MenuWelcome(
        onNavigateToPertanyaan = {
            navController.navigate(Screens.Pertanyaan)
        }
    )

}

@Composable
private fun MenuWelcome(
    modifier: Modifier = Modifier,
    onNavigateToPertanyaan: () -> Unit,
) {


    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selamat datang di aplikasi habits",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Sebelum memulai mohon jawab beberapa pertanyaan berikut",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = onNavigateToPertanyaan) {
                    Text(text = "Ayo Mulai")
                }
            }
        }
    }
}