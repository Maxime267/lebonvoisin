package com.example.lebonvoisin.repository

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
    suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    fun signOut() {
        auth.signOut()
    }

     fun getCurrentUser() = auth.currentUser

    // Force la mise à jour du token d'authentification pour s'assurer qu'on a un token valide
    suspend fun awaitCurrentToken() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.getIdToken(true)?.await()
    }

    suspend fun reauthenticate(password: String) {
        val user = auth.currentUser
            ?: throw Exception("Utilisateur non connecté")

        val email = user.email
            ?: throw Exception("Email introuvable")

        val credential = EmailAuthProvider.getCredential(
            email,
            password
        )

        user.reauthenticate(credential).await()
    }

    suspend fun updatePassword(newPassword: String) {
        auth.currentUser?.updatePassword(newPassword)?.await() ?: throw Exception("update password failed")
    }
    suspend fun updateEmail(newEmail: String) {
        auth.currentUser?.verifyBeforeUpdateEmail(newEmail)?.await() ?: throw Exception("verify email failed")
    //auth.currentUser?.updateEmail(newEmail)?.await() ?: throw Exception("update email failed")
    }
}