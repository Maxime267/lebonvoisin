package com.example.lebonvoisin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.lebonvoisin.navigation.AppScaffold
import com.example.lebonvoisin.navigation.AuthNavGraph
import com.example.lebonvoisin.navigation.RootNavGraph
import com.example.lebonvoisin.ui.theme.LebonvoisinTheme
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryMain : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LebonvoisinTheme {
                RootNavGraph()
            }
        }
    }
}


@Composable
fun MainScreen() {
    RootNavGraph()
}