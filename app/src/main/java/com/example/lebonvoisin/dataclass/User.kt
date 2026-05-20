package com.example.lebonvoisin.dataclass

//stock les info exterieur à firebase auth
data class User(
    val name : String,
    val email : String, //stocke en double(firebase auth) pour pouvoir faire la recherche par email et par nom
    val phone : String?,
    val profilePictureUrl : String? = null,
    val inscriptionDate : String,
    val neighboorhoodName : String? = null
    //on ne prend pas le mot de passe pour des raisons de sécurité, on utilise firebase auth pour ça
)
