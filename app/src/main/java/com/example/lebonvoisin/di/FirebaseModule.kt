package com.example.lebonvoisin.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

/*
 * Module Hilt qui fournit les dépendances Firebase à toute l'application.
 *
 * Hilt utilise ce module pour savoir comment créer :
 * - FirebaseFirestore
 * - FirebaseAuth
 *
 * Grâce à ça, on peut injecter ces objets directement dans
 * les repositories, viewmodels ou autres classes avec @Inject.
 *
 * Exemple :
 *
 * class UserRepository @Inject constructor(
 *     private val firestore: FirebaseFirestore
 * )
 *
 * @InstallIn(SingletonComponent::class)
 * signifie que les instances seront partagées
 * dans toute l'application (singleton global).
 */


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    //hilt regarde le type de retour
    //decide que donc quand on demande FirebaseFirestore c'est la fonction provideFirestore qu'on appel
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance() //accès à fireBase (.collection par exemple)
    }

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance() //donne l'authentification (pour cybersécurité)
    }
}