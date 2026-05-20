package com.example.lebonvoisin.repository

import com.example.lebonvoisin.dataclass.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Toutes les modification sur user (sauf authentification, car different extension firebase)
 */
class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    suspend fun getCurrentUser(): User? {
        val firebaseUser = FirebaseAuth.getInstance().currentUser ?: return null
        return try {
            val snapshot = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()
            snapshot.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun getCurrentUserID() = auth.currentUser?.uid



}