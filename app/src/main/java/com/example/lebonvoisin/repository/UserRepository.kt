package com.example.lebonvoisin.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

/**
 * Toutes les modification sur user (sauf authentification, car different extension firebase)
 */
class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {




}