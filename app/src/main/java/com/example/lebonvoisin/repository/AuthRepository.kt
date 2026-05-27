package com.example.lebonvoisin.repository

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
}