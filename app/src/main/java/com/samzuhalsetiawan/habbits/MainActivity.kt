package com.samzuhalsetiawan.habbits

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.samzuhalsetiawan.habbits.constants.NOTIFICATION_CHANNEL_ID
import com.samzuhalsetiawan.habbits.ui.composable.bottombar.ProvideBottomNavBar
import com.samzuhalsetiawan.habbits.ui.composable.fab.ProvideFloatingActionButton
import com.samzuhalsetiawan.habbits.ui.composable.topbar.ProvideTopAppBar
import com.samzuhalsetiawan.habbits.ui.handler.LoginStateHandler
import com.samzuhalsetiawan.habbits.ui.handler.PopUpDialogStateHandler
import com.samzuhalsetiawan.habbits.ui.localcomposition.ProvideLocalComposition
import com.samzuhalsetiawan.habbits.ui.theme.HabbitsTheme


class MainActivity : ComponentActivity() {

   private val mainViewModel by lazy { initMainViewModel() }

   @OptIn(ExperimentalMaterial3Api::class)
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      initSplashScreen()
      createNotificationChannel()
      enableEdgeToEdge()

      setContent {
         HabbitsTheme {

            val navController = rememberNavController()
            val topAppBarState = rememberTopAppBarState()
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
            val popUpDialogState by mainViewModel.popUpDialogState.collectAsStateWithLifecycle()
            val lifecycleOwner = LocalLifecycleOwner.current
            val snackbarHostState = remember { SnackbarHostState() }
            val coroutineScope = rememberCoroutineScope()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val startDestination by mainViewModel.startDestination.collectAsStateWithLifecycle()

            LoginStateHandler(
               navController = navController,
               lifecycleOwner = lifecycleOwner
            )

            ProvideLocalComposition(
               snackbarHostState = snackbarHostState,
               popUpManager = mainViewModel,
               coroutineScope = coroutineScope,
               navController = navController,
            ) {
               Scaffold(
                  modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                  topBar = { ProvideTopAppBar(scrollBehavior, navBackStackEntry) },
                  floatingActionButton = {
                     ProvideFloatingActionButton(
                        navController,
                        navBackStackEntry
                     )
                  },
                  bottomBar = { ProvideBottomNavBar(navController, navBackStackEntry) },
                  snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
               ) { innerPadding ->

                  MainNavigation(
                     navController = navController,
                     startDestination = startDestination,
                     modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                  )

               }
               PopUpDialogStateHandler(
                  popUpDialogState = popUpDialogState,
                  popUpManager = mainViewModel
               )
            }
         }
      }
   }

   private fun initMainViewModel(): MainViewModel {
      return ViewModelProvider(
         owner = this,
         factory = viewModelFactory {
            addInitializer(MainViewModel::class) {
               MainViewModel(App.repositoryModule.mainRepository)
            }
            build()
         }
      )[MainViewModel::class]
   }

   private fun initSplashScreen() {
      installSplashScreen().apply {
         setKeepOnScreenCondition {
            !MainViewModel.isAppReady
         }
      }
   }

   private fun createNotificationChannel() {
      // Create the NotificationChannel, but only on API 26+ because
      // the NotificationChannel class is not in the Support Library.
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         val name = getString(R.string.channel_name)
         val descriptionText = getString(R.string.channel_description)
         val importance = NotificationManager.IMPORTANCE_DEFAULT
         val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
            description = descriptionText
         }
         // Register the channel with the system.
         val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
         notificationManager.createNotificationChannel(channel)
      }
   }


}