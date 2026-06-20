package com.example.lebonvoisin.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lebonvoisin.view.annonces.MesAnnonces
import com.example.lebonvoisin.view.authentification.AuthScreen
import com.example.lebonvoisin.view.home.HomeScreen
import com.example.lebonvoisin.view.appBar.AppBar
import com.example.lebonvoisin.view.authentification.AuthScreen
import com.example.lebonvoisin.view.authentification.inscription
import com.example.lebonvoisin.view.home.HomeScreen
import com.example.lebonvoisin.view.message.ConversationScreen
import com.example.lebonvoisin.view.message.MessageScreen
import com.example.lebonvoisin.view.profile.Profile
import com.example.lebonvoisin.view.profile.Modify_Profile
import com.example.lebonvoisin.view.profile.Parameters
import com.example.lebonvoisin.view.review.CreateReview
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel


@Composable
fun RootNavGraph(authViewModel: AuthViewModel = hiltViewModel()) {

    val navController = rememberNavController()
    val currentUser = authViewModel.firebaseCurrentUser.value

    NavHost(
        navController = navController,
        startDestination = if (currentUser != null) "app" else "auth"
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
    val appNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            AppBar(
                onHomeClick = { appNavController.navigate("home") },
                onSearchClick = { appNavController.navigate("add") },
                onProfileClick = { appNavController.navigate("profile") } ,
                onMessageClick = { appNavController.navigate("message") }
            )
        }
    ) { padding ->

        Box(modifier = Modifier.padding(padding)) {
            AppNavGraph(appNavController)
        }
    }
}




@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController = navController) }
        composable("add") { MesAnnonces() }
        composable("message") { MessageScreen(navController = navController) }
        composable("conversation/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            ConversationScreen(
                userId = userId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "review/{vendorId}",
            arguments = listOf(
                navArgument("vendorId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val vendorId = backStackEntry.arguments?.getString("vendorId") ?: ""

            CreateReview(
                sellerId = vendorId,
                navController = navController
            )
        }
        composable("profile") { Profile(navController =  navController) }
        composable("parameters") { Parameters(navController = navController) }
        composable("modify") { Modify_Profile(navController = navController) }
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