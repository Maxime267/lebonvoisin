package com.example.lebonvoisin.repository

import com.example.lebonvoisin.dataclass.Annonce
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject





/**
 * Toutes les modification sur Annonce
 */

class AnnonceRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun publier(annonce: Annonce): String {
        annonce.ownerId = FirebaseAuth.getInstance().currentUser?.uid ?: "Anonyme"
        return try {
            firestore.collection("annonces")
                .add(annonce)
                .await() // attend la fin de l'opération
            "Annonce publiée"
        } catch (e: Exception) {
            "Erreur lors de la publication: ${e.message}"
        }
    }
    suspend fun getAnnoncesByUser(userId: String): List<Annonce> {
        return try {
            val snapshot = firestore.collection("annonces")
                .whereEqualTo("ownerId", userId)
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(Annonce::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

}