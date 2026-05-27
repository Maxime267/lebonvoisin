package com.example.lebonvoisin.view.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel

@Composable
fun Profile(modifier: Modifier = Modifier) {

    val authViewModel : AuthViewModel = hiltViewModel()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Header profil
        item {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Localisation") // TODO DB
            Text(text = "Membre depuis ...") // TODO DB
        }

        // Note
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Note",
                        tint = Color(0xFFFFC107)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("4.8 / 5") // TODO DB
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("(32 avis)") // TODO DB
                }
            }
        }

        // Services / box
        item {
            service_box()
        }

        // Annonces
        item {
            Text(
                text = "Mes annonces",
                style = MaterialTheme.typography.titleMedium
            )
        }

        items(5) { index ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Annonce $index",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Actions
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Modifier profil")
                }

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Paramètres")
                }

                Button(
                    onClick = {authViewModel.signOut() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Se déconnecter", color = Color.White)
                }
            }
        }
    }
}