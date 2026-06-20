package com.example.lebonvoisin.dataclass

data class Conversation(
    val userId: String = "",
    val userName: String = "",
    val annonceTitre: String = "",
    val dernierMessage: Message = Message(),
    val nombreMessages: Int = 0
)