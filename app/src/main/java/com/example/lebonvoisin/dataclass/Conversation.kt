package com.example.lebonvoisin.dataclass

data class Conversation(
    val annonceId: String = "",
    val annonceTitre: String = "",
    val otherUserId: String = "",
    val otherUserName: String = "",
    val proprietaireId: String = "",
    val proprietaireNom: String = "",
    val dernierMessage: Message = Message(),
    val nombreMessages: Int = 0
)