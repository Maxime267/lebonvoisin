package com.example.lebonvoisin.viewmodel.authentification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lebonvoisin.dataclass.User
import com.example.lebonvoisin.repository.AuthRepository
import com.example.lebonvoisin.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * ViewModel simple pour gérer l'authentification Firebase.
 * Fournit l'utilisateur courant et des méthodes pour se connecter / inscrire / déconnecter.
 */

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository
) : ViewModel() {

    var user = mutableStateOf<User>(
        User(
            name = "",
            email = "",
            phone = "",
            profilePictureUrl = "",
            inscriptionDate = 0L,
            neighborhoodName = ""
        )
    )

    val firebaseCurrentUser = mutableStateOf<FirebaseUser?>(authRepository.getCurrentUser())
    val message = mutableStateOf<String>("")

    init {
        // Listener Firebase pour les changements d'authentification
        firebaseAuth.addAuthStateListener { auth ->
            firebaseCurrentUser.value = auth.currentUser
        }
    }


    fun updateName(newName: String) {
        user.value = user.value.copy(name = newName)
    }
    fun updateEmail(newEmail: String) {
        user.value = user.value.copy(email = newEmail)
    }
    fun updatePhone(newPhone: String) {
        user.value = user.value.copy(phone = newPhone)
    }
    fun updateProfilePicture(newUrl: String) {
        user.value = user.value.copy(profilePictureUrl = newUrl)
    }
    fun updateNeighborhood(newNeighborhood: String) {
        user.value = user.value.copy(neighborhoodName = newNeighborhood)
    }





    suspend fun connection(email: String, password: String) {
        try {
            authRepository.signIn(email, password)
            // Le listener Firebase se chargera de mettre à jour currentUser
            message.value = "Connexion réussie"
        } catch (e: Exception) {
            message.value = "Erreur: ${e.message}"
        }
    }

    suspend fun inscription(password: String) {
        try {
            authRepository.signUp(user.value.email, password)
            authRepository.awaitCurrentToken()
            userRepository.createUserIfNotExists(user.value)
            message.value = "Connexion réussie"
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            message.value = "Erreur: ${e.localizedMessage}"
        }
    }


    fun signOut() {
        authRepository.signOut()
        // Le listener Firebase se chargera de mettre à jour currentUser
        message.value = "Déconnecté"
    }
}