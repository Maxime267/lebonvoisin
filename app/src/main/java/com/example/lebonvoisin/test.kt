package com.example.lebonvoisin

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lebonvoisin.view.authentification.AuthScreen
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun MainScreen() {
    // ViewModel d'authentification (préférer viewModel() pour le cycle de vie)
    // demande explicitement le type pour que viewModel() sache quelle classe créer
    val authViewModel: AuthViewModel = viewModel<AuthViewModel>()

    val db = FirebaseFirestore.getInstance()
    var message by remember { mutableStateOf("Aucun test") }

    Column {
        // Ecran d'authentification (inscription / connexion / déconnexion)
        AuthScreen(viewModel = authViewModel)

        Text(text = "Statut: ${authViewModel.currentUser.value?.email ?: "non connecté"}")
        Text(text = message)

        Button(onClick = {
            // Protège l'écriture: n'autorise que les utilisateurs connectés
            val user = authViewModel.currentUser.value
            if (user == null) {
                message = "❗ Connectez-vous pour écrire dans Firestore"
                return@Button
            }

            val data = hashMapOf(
                "name" to (user.email ?: "ComposeUser"),
                "age" to 20,
                "uid" to user.uid
            )

            db.collection("test")
                .add(data)
                .addOnSuccessListener {
                    message = "🔥 Données ajoutées avec succès"
                }
                .addOnFailureListener {
                    message = "❌ Erreur Firestore : ${it.message}"
                }

        }) {
            Text("Test Firestore Write (requiert connexion)")
        }
    }
}
