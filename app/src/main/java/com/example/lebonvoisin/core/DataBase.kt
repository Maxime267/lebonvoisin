package com.example.lebonvoisin.core

import com.google.firebase.firestore.FirebaseFirestore

class DataBase {
    val db = FirebaseFirestore.getInstance()

    fun add(){
        val annonces = hashMapOf(
            "nom" to "Garder de chien",
            "description" to "Je suis disponible pour garder vos animaux ( voir mp pour le prix)",
            "action" to "service",
            "personne" to "Eliot.N",
            "rue" to "Rue de Gustave Courbet"
        )

        db.collection("annonces")
            .add(annonces)
            .addOnSuccessListener {
                println("Ajout réussi")
            }
            .addOnFailureListener {
                println("Erreur")
            }
    }

    fun read(onResult: (List<Map<String, String>>) -> Unit){
        val annoncesList = mutableListOf<Map<String, String>>()
        db.collection("annonces")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val annonce = mapOf(
                        "nom" to (document.getString("nom") ?: ""),
                        "description" to (document.getString("description") ?: ""),
                        "action" to (document.getString("action") ?: ""),
                        "personne" to (document.getString("personne") ?: ""),
                        "rue" to (document.getString("rue") ?: "")
                    )

                    annoncesList.add(annonce)

                }
                onResult(annoncesList)
            }
            .addOnFailureListener { exception ->
                println("Erreur lors de la lecture : ${exception.message}")
                onResult(emptyList())
            }

    }
}