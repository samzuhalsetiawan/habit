package com.samzuhalsetiawan.habbits.ui.screen.menu_login

import android.content.Context
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.habbits.constants.WEB_CLIENT_ID
import com.samzuhalsetiawan.habbits.repository.MainRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MenuLoginViewModel(
   private val mainRepository: MainRepository
) : ViewModel() {

   fun onLoginWithGoogleButtonClicked(activityContext: Context) {
      viewModelScope.launch {
         signInWithGoogle(activityContext)
      }
   }

   private suspend fun signInWithGoogle(activityContext: Context) {
      val credentialManager = CredentialManager.create(activityContext.applicationContext)

      val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(WEB_CLIENT_ID).build()

      val request = GetCredentialRequest.Builder()
         .addCredentialOption(signInWithGoogleOption)
         .build()

      viewModelScope.launch {
         try {
            val result = credentialManager.getCredential(
               context = activityContext,
               request = request
            )

            val credential = result.credential

            val googleIdTokenCredential = GoogleIdTokenCredential
               .createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            Firebase.auth.signInWithCredential(firebaseCredential).await()
            mainRepository.setIsLogin(true)
         } catch (e: Exception) {
            Toast.makeText(activityContext, "Login Error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
         }
      }
   }
}