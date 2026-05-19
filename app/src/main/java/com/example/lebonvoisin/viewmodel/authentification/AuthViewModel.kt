package com.example.lebonvoisin.viewmodel.authentification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lebonvoisin.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel simple pour gérer l'authentification Firebase.
 * Fournit l'utilisateur courant et des méthodes pour se connecter / inscrire / déconnecter.
 */

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    val currentUser = mutableStateOf<FirebaseUser?>(repository.getCurrentUser())
    val message = mutableStateOf<String>("")

    init {
        // Listener Firebase pour les changements d'authentification
        firebaseAuth.addAuthStateListener { auth ->
            currentUser.value = auth.currentUser
        }
    }

    suspend fun signIn(email: String, password: String) {
        try {
            repository.signIn(email, password)
            // Le listener Firebase se chargera de mettre à jour currentUser
            message.value = "Connexion réussie"
        } catch (e: Exception) {
            message.value = "Erreur: ${e.message}"
        }
    }

    suspend fun signUp(email: String, password: String) {
        try {
            repository.signUp(email, password)
            // Le listener Firebase se chargera de mettre à jour currentUser
            message.value = "Inscription réussie"
        } catch (e: Exception) {
            message.value = "Erreur: ${e.message}"
        }
    }

    fun signOut() {
        repository.signOut()
        // Le listener Firebase se chargera de mettre à jour currentUser
        message.value = "Déconnecté"
    }
}