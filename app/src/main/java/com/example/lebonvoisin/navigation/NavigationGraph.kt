package com.example.lebonvoisin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lebonvoisin.view.annonces.MesAnnonces
import com.example.lebonvoisin.view.home.HomeScreen
import com.example.lebonvoisin.view.profile.Profile

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") { HomeScreen() }
        composable("add") { MesAnnonces() }
        composable("profile") { Profile() }
    }
}