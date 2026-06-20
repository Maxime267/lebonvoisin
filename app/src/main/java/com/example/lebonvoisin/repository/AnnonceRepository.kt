package com.example.lebonvoisin.repository

import android.util.Log
import com.example.lebonvoisin.dataclass.Annonce
import com.example.lebonvoisin.dataclass.review.Review
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
    suspend fun getAnnoncesCurrentUser(): List<Annonce> {
        return try {
            val userId = firebaseAuth.currentUser?.uid ?: return emptyList()
            val snapshot = firestore.collection("annonces")
                .whereEqualTo("ownerId", userId)
                .get()
                .await()
            snapshot.documents.map { document ->
                Annonce(
                    id = document.getLong("id")?.toInt() ?: 0,
                    titre = document.getString("titre") ?: "",
                    description = document.getString("description") ?: "",
                    typeService = document.getString("typeService") ?: "",
                    personne = document.getString("personne") ?: "",
                    rue = document.getString("rue") ?: "",
                    action = document.getString("action") ?: ""
                )
            }
        } catch (e: Exception) {
            Log.e("FIRESTORE_ERROR", "Erreur getAnnoncesByUser", e)
            emptyList()
        }
    }

    suspend fun getAnnoncesAll(): List<Annonce> {
        return try {
            val snapshot = firestore.collection("annonces")
                .get()
                .await()

            snapshot.documents.map { document ->
                Annonce(
                    id = document.getLong("id")?.toInt() ?: 0,
                    titre = document.getString("titre") ?: "",
                    description = document.getString("description") ?: "",
                    typeService = document.getString("typeService") ?: "",
                    personne = document.getString("personne") ?: "",
                    rue = document.getString("rue") ?: "",
                    action = document.getString("action") ?: "",
                    ownerId = document.getString("ownerId") ?:"Failed to get"
                )
            }
        } catch (e: Exception) {
            Log.e("FIRESTORE_ERROR", "Erreur getAnnoncesAll", e)
            emptyList()
        }
    }


}