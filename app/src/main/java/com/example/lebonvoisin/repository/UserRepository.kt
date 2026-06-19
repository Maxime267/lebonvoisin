package com.example.lebonvoisin.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import com.example.lebonvoisin.dataclass.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.util.Calendar
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

    fun createUserIfNotExists(user: User) {
        try {

            val userId = auth.currentUser?.uid ?: return
            val userRef = firestore.collection("users").document(userId)

            userRef.get().addOnSuccessListener { document ->
                if (!document.exists()) {
                    val user = user.copy(Calendar.getInstance().time.toString())
                    userRef.set(user)
                }
            }
        } catch (e: Exception) {
            Log.e("FIRESTORE_ERROR", "Erreur createUserIfNotExists", e)
        }
    }

    suspend fun updateUser(user: User): String {
        return try {
            val userId = auth.currentUser?.uid ?: return "Utilisateur non connecté"
            firestore.collection("users")
                .document(userId)
                .set(user)
                .await()
            "Profil mis à jour"
        } catch (e: Exception) {
            Log.e("FIRESTORE_ERROR", "Erreur updateUser", e)
            "Erreur lors de la mise à jour du profil: ${e.message}"
        }
    }


}