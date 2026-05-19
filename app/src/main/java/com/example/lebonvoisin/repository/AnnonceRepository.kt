package com.example.lebonvoisin.repository

import com.example.lebonvoisin.dataclass.Annonce
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject





/**
 * Toutes les modification sur Annonce
 */

class AnnonceRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    fun publier(annonce: Annonce) {
        // TODO API / Firebase / DB
    }
}