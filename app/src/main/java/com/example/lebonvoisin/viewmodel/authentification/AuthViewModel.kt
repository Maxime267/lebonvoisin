package com.example.lebonvoisin.viewmodel.authentification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * ViewModel simple pour gérer l'authentification Firebase.
 * Fournit l'utilisateur courant et des méthodes pour se connecter / inscrire / déconnecter.
 */

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // utilisateur courant exposé en State pour Compose
    val currentUser = mutableStateOf<FirebaseUser?>(auth.currentUser)

    // message d'état simple
    val message = mutableStateOf<String>("")

    private val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        currentUser.value = firebaseAuth.currentUser
    }

    init {
        auth.addAuthStateListener(authListener)
    }

    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authListener)
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // La connexion a réussi
                    val user = auth.currentUser
                    message.value = "Auth" + "Connexion réussie: ${user?.uid}"
                } else {
                    // La connexion a échoué
                    message.value = "Auth" + "Échec de la connexion" + task.exception
                }
            }
    }

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // L'inscription a réussi
                    val user = auth.currentUser
                    message.value = "Auth" + "Inscription réussie: ${user?.uid}"
                    // Ici, l'utilisateur est automatiquement connecté
                } else {
                    // L'inscription a échoué
                    message.value =  "Auth" + "Échec de l'inscription" + task.exception
                }
            }
    }

    fun signOut() {
        auth.signOut()
        currentUser.value = null
        message.value = "Déconnecté"
    }
}