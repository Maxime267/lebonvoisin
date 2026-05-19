package com.example.lebonvoisin.view.authentification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel


/**
 * Petit écran Compose pour gérer l'authentification (inscription / connexion / déconnexion).
 * Utilise AuthViewModel pour effectuer les actions.
 */
@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Authentification")

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.padding(top = 8.dp)) {
            Button(onClick = { viewModel.signIn(email.trim(), password) }, modifier = Modifier.weight(1f)) {
                Text("Se connecter")
            }

            Button(onClick = { viewModel.signUp(email.trim(), password) }, modifier = Modifier.weight(1f)) {
                Text("S'inscrire")
            }
        }

        // Déconnexion
        if (viewModel.currentUser.value != null) {
            Button(onClick = { viewModel.signOut() }, modifier = Modifier.padding(top = 8.dp)) {
                Text("Se déconnecter")
            }
        }

        // Message d'état
        Text(text = viewModel.message.value, modifier = Modifier.padding(top = 8.dp))
    }
}

