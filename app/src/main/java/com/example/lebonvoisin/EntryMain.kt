package com.example.lebonvoisin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.lebonvoisin.navigation.AppNavGraph
import com.example.lebonvoisin.ui.theme.LebonvoisinTheme
import com.example.lebonvoisin.view.pAppBar.AppBar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryMain : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LebonvoisinTheme {
                MainScreen()
            }
        }
    }
}

/*
@Composable
fun MainScreen() {

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
*/
