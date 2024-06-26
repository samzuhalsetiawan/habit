package com.samzuhalsetiawan.habbits.ui.screen.menu_login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.samzuhalsetiawan.habbits.App
import com.samzuhalsetiawan.habbits.R
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.ui.localcomposition.provider.LocalNavigationController
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import com.samzuhalsetiawan.habbits.utils.provideViewModel

@Composable
fun MenuLogin(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val viewModel = provideViewModel {
        MenuLoginViewModel(App.repositoryModule.mainRepository)
    }
    val navController = LocalNavigationController.current

    MenuLogin(
        modifier = modifier,
        onLoginWithGoogleButtonClicked = { viewModel.onLoginWithGoogleButtonClicked(context) },
        onRegisterHereButtonClicked = { navController.navigate(Screens.Register) }
    )
}

@Composable
private fun MenuLogin(
    onLoginWithGoogleButtonClicked: () -> Unit,
    onRegisterHereButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
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
                        text = "Sign In",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 24.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "Masuk dengan akun yang sudah ada atau lewati untuk masuk sebagai Guest",
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
                        onValueChange = {  },
                        label = { Text("Email") }
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = "",
                        onValueChange = {  },
                        label = { Text("Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    )
                    Button(
                        modifier = Modifier.padding(vertical = 24.dp),
                        onClick = { /*TODO*/ },
                    ) {
                        Text(text = "Masuk")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        HorizontalDivider(modifier = Modifier.width(70.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "Atau masuk dengan",
                            style = MaterialTheme.typography.bodySmall
                        )
                        HorizontalDivider(modifier = Modifier.width(70.dp))
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable(onClick = onLoginWithGoogleButtonClicked),
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "Google"
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Image(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = "Google"
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Belum punya akun?",
                            style = MaterialTheme.typography.bodySmall
                        )
                        TextButton(onClick = onRegisterHereButtonClicked) {
                            Text(
                                text = "Daftar disini",
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
                onClick = { /*TODO*/ }) {
                Text(
                    text = "Lewati"
                )
            }
        }
    }
}