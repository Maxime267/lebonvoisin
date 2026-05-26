package com.example.lebonvoisin.repository

import com.example.lebonvoisin.dataclass.Annonce
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AnnonceRepository @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()

    fun publier(
        annonce: Annonce,
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        val annonceMap = hashMapOf(
            "id" to annonce.id,
            "titre" to annonce.titre,
            "description" to annonce.description,
            "typeService" to annonce.typeService
        )

        db.collection("annonces")
            .add(annonceMap)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun lire(
        onResult: (List<Annonce>) -> Unit
    ) {
        db.collection("annonces")
            .get()
            .addOnSuccessListener { result ->

                val annonces = mutableListOf<Annonce>()

                for (document in result) {
                    val annonce = Annonce(
                        id = document.getLong("id")?.toInt() ?: 0,
                        titre = document.getString("titre") ?: "",
                        description = document.getString("description") ?: "",
                        typeService = document.getString("typeService") ?: ""
                    )

                    annonces.add(annonce)
                }

                onResult(annonces)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }
}