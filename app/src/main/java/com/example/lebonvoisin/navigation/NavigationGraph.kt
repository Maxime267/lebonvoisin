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
import com.example.lebonvoisin.view.authentification.AuthScreen
import com.example.lebonvoisin.view.home.HomeScreen
import com.example.lebonvoisin.view.appBar.AppBar
import com.example.lebonvoisin.view.authentification.inscription
import com.example.lebonvoisin.view.profile.Profile
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel


@Composable
fun RootNavGraph(authViewModel: AuthViewModel = hiltViewModel()) {

    val navController = rememberNavController()
    val currentUser = authViewModel.firebaseCurrentUser.value

    NavHost(
        navController = navController,
        //startDestination = if (currentUser != null) "app" else "auth"
        startDestination = "app"
    ) {

        composable("auth") {
            AuthNavGraph(
                rootNavController = navController,
            )
        }

        composable("app") {
            AppScaffold(navController)
        }
    }
}




@Composable
fun AppScaffold(rootNavController: NavHostController) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            AppBar(
                onHomeClick = { navController.navigate("home") },
                onSearchClick = { navController.navigate("add") },
                onProfileClick = { navController.navigate("profile") }
            )
        }
    ) { padding ->

        Box(modifier = Modifier.padding(padding)) {
            AppNavGraph(navController)
        }
    }
}




@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen() }
        composable("add") { MesAnnonces() }
        composable("profile") { Profile() }
    }
}

@Composable
fun AuthNavGraph(rootNavController : NavHostController) {
    val authNavController = rememberNavController()

    NavHost(authNavController, startDestination = "connection") {
        composable("connection") { AuthScreen(authNavController) }
        composable ("inscription"){ inscription(authNavController) }
    }
}