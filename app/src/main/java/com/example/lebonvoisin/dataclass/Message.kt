package com.example.lebonvoisin.dataclass

data class Message(
    val id: String = "",
    val annonceId: String = "",
    val annonceTitre: String = "",
    val proprietaireId: String = "",
    val proprietaireNom: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val contenu: String = "",
    val date: Long = System.currentTimeMillis(),
    val lu: Boolean = false
)