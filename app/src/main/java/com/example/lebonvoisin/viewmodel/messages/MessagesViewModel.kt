package com.example.lebonvoisin.viewmodel.message

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lebonvoisin.dataclass.Annonce
import com.example.lebonvoisin.dataclass.Conversation
import com.example.lebonvoisin.dataclass.Message
import com.example.lebonvoisin.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val repository: MessageRepository
) : ViewModel() {

    var conversations by mutableStateOf(listOf<Conversation>())
    var messagesConversation by mutableStateOf(listOf<Message>())
    var isLoading by mutableStateOf(false)
    var messageInfo by mutableStateOf("")

    fun chargerConversations() {
        viewModelScope.launch {
            isLoading = true
            conversations = repository.getConversations()
            isLoading = false
        }
    }

    fun chargerConversationAvec(
        annonceId: String,
        otherUserId: String
    ) {
        viewModelScope.launch {
            messagesConversation = repository.getConversationAvec(
                annonceId = annonceId,
                otherUserId = otherUserId
            )
        }
    }

    fun contacterAnnonce(
        annonce: Annonce,
        contenu: String = "Bonjour, je suis intéressé par votre annonce."
    ) {
        viewModelScope.launch {
            val senderId = repository.getCurrentUserId() ?: return@launch

            val proprietaireId = if (annonce.ownerId.isNotBlank()) {
                annonce.ownerId
            } else {
                repository.getUserIdByName(annonce.personne)
            }

            if (proprietaireId == null) {
                messageInfo = "Utilisateur introuvable"
                return@launch
            }

            val message = Message(
                annonceId = annonce.id.toString(),
                annonceTitre = annonce.titre,
                proprietaireId = proprietaireId,
                proprietaireNom = annonce.personne,
                senderId = senderId,
                receiverId = proprietaireId,
                contenu = contenu
            )

            messageInfo = repository.envoyerMessage(message)
        }
    }

    fun envoyerMessageConversation(
        annonceId: String,
        otherUserId: String,
        contenu: String
    ) {
        viewModelScope.launch {
            val senderId = repository.getCurrentUserId() ?: return@launch
            val ancienMessage = messagesConversation.firstOrNull()

            val message = Message(
                annonceId = annonceId,
                annonceTitre = ancienMessage?.annonceTitre ?: "",
                proprietaireId = ancienMessage?.proprietaireId ?: "",
                proprietaireNom = ancienMessage?.proprietaireNom ?: "",
                senderId = senderId,
                receiverId = otherUserId,
                contenu = contenu
            )

            messageInfo = repository.envoyerMessage(message)
            chargerConversationAvec(annonceId, otherUserId)
        }
    }
}