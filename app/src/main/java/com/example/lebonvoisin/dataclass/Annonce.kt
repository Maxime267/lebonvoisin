package com.example.lebonvoisin.dataclass

data class Annonce(
    val id : Int,
    val titre: String,
    val description: String,
    val typeService: String,
    var ownerId : String = "Oublie de changer" ,// ID de l'utilisateur qui a publié l'annonce
    val personne : String,
    val rue : String,
    val action : String
)
