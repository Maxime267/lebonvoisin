package com.example.lebonvoisin.viewmodel.authentification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lebonvoisin.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel simple pour gérer l'authentification Firebase.
 * Fournit l'utilisateur courant et des méthodes pour se connecter / inscrire / déconnecter.
 */

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    val currentUser = mutableStateOf(repository.getCurrentUser())
    val message = mutableStateOf<String>("")

    suspend fun signIn(email: String, password: String) {
        try {
            repository.signIn(email, password)
            currentUser.value = repository.getCurrentUser()
            message.value = "Connexion réussie"
        } catch (e: Exception) {
            message.value = "Erreur: ${e.message}"
        }
    }

    suspend fun signUp(email: String, password: String) {
        try {
            repository.signUp(email, password)
            currentUser.value = repository.getCurrentUser()
            message.value = "Inscription réussie"
        } catch (e: Exception) {
            message.value = "Erreur: ${e.message}"
        }
    }

    fun signOut() {
        repository.signOut()
        currentUser.value = null
        message.value = "Déconnecté"
    }
}