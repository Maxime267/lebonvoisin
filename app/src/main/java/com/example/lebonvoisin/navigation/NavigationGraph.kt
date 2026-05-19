package com.example.lebonvoisin.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lebonvoisin.view.annonces.MesAnnonces
import com.example.lebonvoisin.view.authentification.AuthScreen
import com.example.lebonvoisin.view.home.HomeScreen
import com.example.lebonvoisin.view.appBar.AppBar
import com.example.lebonvoisin.view.profile.Profile





@Composable
fun AppScaffold(navController: NavHostController) {

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
fun AuthNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "auth") {
        composable("auth") { AuthScreen() }
    }
}