package com.example.lebonvoisin.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lebonvoisin.view.annonces.MesAnnonces
import com.example.lebonvoisin.view.appBar.AppBar
import com.example.lebonvoisin.view.authentification.AuthScreen
import com.example.lebonvoisin.view.authentification.inscription
import com.example.lebonvoisin.view.home.HomeScreen
import com.example.lebonvoisin.view.message.ConversationScreen
import com.example.lebonvoisin.view.message.MessageScreen
import com.example.lebonvoisin.view.profile.Profile
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel

@Composable
fun RootNavGraph(authViewModel: AuthViewModel = hiltViewModel()) {

    val rootNavController = rememberNavController()
    val currentUser = authViewModel.firebaseCurrentUser.value

    NavHost(
        navController = rootNavController,
        startDestination = if (currentUser != null) "app" else "auth"
    ) {
        composable("auth") {
            AuthNavGraph(rootNavController = rootNavController)
        }

        composable("app") {
            AppScaffold(rootNavController = rootNavController)
        }
    }
}

@Composable
fun AppScaffold(rootNavController: NavHostController) {

    val appNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            AppBar(
                onHomeClick = {
                    appNavController.navigate("home")
                },
                onSearchClick = {
                    appNavController.navigate("add")
                },
                onMessageClick = {
                    appNavController.navigate("message")
                },
                onProfileClick = {
                    appNavController.navigate("profile")
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier.padding(padding)
        ) {
            AppNavGraph(navController = appNavController)
        }
    }
}

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen()
        }

        composable("add") {
            MesAnnonces()
        }

        composable("message") {
            MessageScreen(navController = navController)
        }

        composable("conversation/{userId}") { backStackEntry ->

            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            ConversationScreen(
                userId = userId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("profile") {
            Profile()
        }
    }
}

@Composable
fun AuthNavGraph(rootNavController: NavHostController) {

    val authNavController = rememberNavController()

    NavHost(
        navController = authNavController,
        startDestination = "connection"
    ) {
        composable("connection") {
            AuthScreen(authNavController)
        }

        composable("inscription") {
            inscription(authNavController)
        }
    }
}