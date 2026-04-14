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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.lebonvoisin.pAppBar.*
import com.example.lebonvoisin.ui.theme.LebonvoisinTheme
import com.example.lebonvoisin.profile.Profile
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
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

@Composable
fun MainScreen() {

    var currentScreen by remember { mutableStateOf(Screen.HOME) }

    Scaffold(
        bottomBar = {
            AppBar(
                onHomeClick = { currentScreen = Screen.HOME },
                onProfileClick = { currentScreen = Screen.PROFILE },
                onSearchClick = {currentScreen = Screen.ADD}
            )
        }
    ) { padding ->

        Box(modifier = Modifier.padding(padding)) {
            when (currentScreen) {
                Screen.HOME -> Text("Home page") //TODO ADD Function
                Screen.ADD -> Text("ADD") //TODO ADD Function
                Screen.PROFILE -> Profile()
            }
        }
    }
}



