package com.example.lebonvoisin.view.appBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun AppBar(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    var selected = remember { mutableStateOf("Home") }

    NavigationBar {

        NavigationBarItem(
            selected = selected.value == "Home",
            onClick = {
                selected.value = "Home"
                onHomeClick()
                      },
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Accueil") }
        )

        NavigationBarItem(
            selected = selected.value == "Ajouter",
            onClick = {
                selected.value = "Ajouter"
                onSearchClick()
                      },
            icon = { Icon(Icons.Default.AddCircle, null) },
            label = { Text("Ajouter") }
        )

        NavigationBarItem(
            selected = selected.value == "Profile",
            onClick = {
                selected.value = "Profile"
                onProfileClick()
                      },
            icon = { Icon(Icons.Default.AccountCircle, null) },
            label = { Text("Profil") }
        )
    }
}