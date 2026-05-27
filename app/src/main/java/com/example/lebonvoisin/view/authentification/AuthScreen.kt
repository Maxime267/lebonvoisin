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
import androidx.navigation.NavHostController
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel
import kotlinx.coroutines.launch


/**
 * Petit écran Compose pour gérer l'authentification (inscription / connexion / déconnexion).
 * Utilise AuthViewModel pour effectuer les actions.
 */
@Composable
fun AuthScreen(navController : NavHostController ) {
    val viewModel: AuthViewModel = hiltViewModel()
    var password by remember { mutableStateOf("") }
    val user = viewModel.user.value
    val scope = rememberCoroutineScope() // scope pour lancer les appels suspend de viewModel

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Authentification")

        OutlinedTextField(
            value = user.email,
            onValueChange = { viewModel.updateEmail(it) },
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
            Button(onClick = { scope.launch { viewModel.connection(email = user.email, password = password) } }, modifier = Modifier.weight(1f)) {
                Text("Se connecter")
            }

            Button(onClick = { navController.navigate("inscription") }, modifier = Modifier.weight(1f)) {
                Text("S'inscrire")
            }
        }

        // Déconnexion
        if (viewModel.firebaseCurrentUser.value != null) {
            Button(onClick = { viewModel.signOut() }, modifier = Modifier.padding(top = 8.dp)) {
                Text("Se déconnecter")
            }
        }

        // Message d'état
        Text(text = viewModel.message.value, modifier = Modifier.padding(top = 8.dp))
    }
}

