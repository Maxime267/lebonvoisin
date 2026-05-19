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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lebonvoisin.viewmodel.annonces.MesAnnoncesViewModel
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel
import kotlinx.coroutines.launch


/**
 * Petit écran Compose pour gérer l'authentification (inscription / connexion / déconnexion).
 * Utilise AuthViewModel pour effectuer les actions.
 */
@Composable
fun AuthScreen() {
    val viewModel: AuthViewModel = hiltViewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope() // scope pour lancer les appels suspend de viewModel

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
            Button(onClick = { scope.launch { viewModel.signIn(email.trim(), password) } }, modifier = Modifier.weight(1f)) {
                Text("Se connecter")
            }

            Button(onClick = { scope.launch { viewModel.signUp(email.trim(), password) } }, modifier = Modifier.weight(1f)) {
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

