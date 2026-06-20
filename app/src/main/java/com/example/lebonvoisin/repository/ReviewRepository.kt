package com.example.lebonvoisin.repository

import com.example.lebonvoisin.dataclass.review.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun createReview(review: Review) : String{
        review.ownerId = FirebaseAuth.getInstance().currentUser?.uid ?: "Anonyme"
        return try {
            firestore.collection("review")
                .add(review)
                .await() // attend la fin de l'opération
            "Annonce publiée"
        } catch (e: Exception) {
            "Erreur lors de la publication: ${e.message}"
        }

    }

    suspend fun loadReviewsByOwnerCurrentUser(): List<Review> {
        val currentUserId = firebaseAuth.currentUser?.uid ?: return emptyList()

        return try {
            val snapshot = firestore.collection("review")
                .whereEqualTo("ownerId", currentUserId)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Review::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun loadReviewsBySellerCurrentUser(): List<Review> {
        val currentUserId = firebaseAuth.currentUser?.uid ?: return emptyList()

        return try {
            val snapshot = firestore.collection("review")
                .whereEqualTo("sellerId", currentUserId)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Review::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
