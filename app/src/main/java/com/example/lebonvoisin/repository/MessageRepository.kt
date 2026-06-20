package com.example.lebonvoisin.repository

import com.example.lebonvoisin.dataclass.Conversation
import com.example.lebonvoisin.dataclass.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    suspend fun envoyerMessage(message: Message): String {
        return try {
            firestore.collection("messages")
                .add(message)
                .await()

            "Message envoyé"
        } catch (e: Exception) {
            "Erreur : ${e.message}"
        }
    }

    suspend fun getUserIdByName(nom: String): String? {
        return try {
            val snapshot = firestore.collection("users")
                .whereEqualTo("nom", nom)
                .get()
                .await()

            snapshot.documents.firstOrNull()?.id
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getConversationsRecues(): List<Conversation> {
        val currentUserId = auth.currentUser?.uid ?: return emptyList()

        return try {
            val snapshot = firestore.collection("messages")
                .whereEqualTo("receiverId", currentUserId)
                .get()
                .await()

            val messages = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Message::class.java)?.copy(id = doc.id)
            }

            messages
                .groupBy { it.annonceId }
                .map { (_, messagesAnnonce) ->
                    val dernierMessage = messagesAnnonce.maxByOrNull { it.date } ?: Message()

                    Conversation(
                        userId = dernierMessage.senderId,
                        userName = dernierMessage.proprietaireNom,
                        annonceTitre = dernierMessage.annonceTitre,
                        dernierMessage = dernierMessage,
                        nombreMessages = messagesAnnonce.size
                    )
                }
                .sortedByDescending { it.dernierMessage.date }

        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getConversationAvec(userId: String): List<Message> {
        val currentUserId = auth.currentUser?.uid ?: return emptyList()

        return try {
            val recus = firestore.collection("messages")
                .whereEqualTo("senderId", userId)
                .whereEqualTo("receiverId", currentUserId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Message::class.java)?.copy(id = it.id) }

            val envoyes = firestore.collection("messages")
                .whereEqualTo("senderId", currentUserId)
                .whereEqualTo("receiverId", userId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Message::class.java)?.copy(id = it.id) }

            (recus + envoyes).sortedBy { it.date }

        } catch (e: Exception) {
            emptyList()
        }
    }
}