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
                .add(message.copy(date = System.currentTimeMillis()))
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

    suspend fun getUserNameById(userId: String): String {
        return try {
            val document = firestore.collection("users")
                .document(userId)
                .get()
                .await()

            document.getString("nom")
                ?: document.getString("name")
                ?: document.getString("prenom")
                ?: "Utilisateur"
        } catch (e: Exception) {
            "Utilisateur"
        }
    }

    suspend fun getConversations(): List<Conversation> {
        val currentUserId = auth.currentUser?.uid ?: return emptyList()

        return try {
            val messagesRecus = firestore.collection("messages")
                .whereEqualTo("receiverId", currentUserId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Message::class.java)?.copy(id = it.id) }

            val messagesEnvoyes = firestore.collection("messages")
                .whereEqualTo("senderId", currentUserId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Message::class.java)?.copy(id = it.id) }

            val allMessages = messagesRecus + messagesEnvoyes

            allMessages
                .groupBy { message ->
                    val otherUserId = if (message.senderId == currentUserId) {
                        message.receiverId
                    } else {
                        message.senderId
                    }

                    "${message.annonceId}_$otherUserId"
                }
                .map { (_, messagesConversation) ->

                    val dernierMessage = messagesConversation.maxByOrNull { it.date } ?: Message()

                    val otherUserId = if (dernierMessage.senderId == currentUserId) {
                        dernierMessage.receiverId
                    } else {
                        dernierMessage.senderId
                    }

                    val otherUserName = getUserNameById(otherUserId)

                    Conversation(
                        annonceId = dernierMessage.annonceId,
                        annonceTitre = dernierMessage.annonceTitre,
                        otherUserId = otherUserId,
                        otherUserName = otherUserName,
                        proprietaireId = dernierMessage.proprietaireId,
                        proprietaireNom = dernierMessage.proprietaireNom,
                        dernierMessage = dernierMessage,
                        nombreMessages = messagesConversation.size
                    )
                }
                .sortedByDescending { it.dernierMessage.date }

        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getConversationAvec(
        annonceId: String,
        otherUserId: String
    ): List<Message> {
        val currentUserId = auth.currentUser?.uid ?: return emptyList()

        return try {
            val messages1 = firestore.collection("messages")
                .whereEqualTo("annonceId", annonceId)
                .whereEqualTo("senderId", currentUserId)
                .whereEqualTo("receiverId", otherUserId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Message::class.java)?.copy(id = it.id) }

            val messages2 = firestore.collection("messages")
                .whereEqualTo("annonceId", annonceId)
                .whereEqualTo("senderId", otherUserId)
                .whereEqualTo("receiverId", currentUserId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Message::class.java)?.copy(id = it.id) }

            (messages1 + messages2).sortedBy { it.date }

        } catch (e: Exception) {
            emptyList()
        }
    }
}