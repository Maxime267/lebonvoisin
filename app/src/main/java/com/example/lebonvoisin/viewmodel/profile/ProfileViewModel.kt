package com.example.lebonvoisin.viewmodel.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lebonvoisin.dataclass.User
import com.example.lebonvoisin.repository.AnnonceRepository
import com.example.lebonvoisin.repository.AuthRepository
import com.example.lebonvoisin.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val annonceRepository: AnnonceRepository
) : ViewModel() {

    //=================
    // User Info
    //=================

    var user by mutableStateOf<User>(
        User(
            name = "",
            email = "",
            phone = "",
            profilePictureUrl = "",
            inscriptionDate = 0L,
            neighborhoodName = ""
        )
    )
    val userID by lazy { userRepository.getCurrentUserID() }

    fun loadUserInfo() {
        viewModelScope.launch {
            var currentUser = userRepository.getCurrentUser()
            if(currentUser == null){
                authRepository.signOut()
            }
            user = currentUser ?: user
            newUser = currentUser ?: user

            loadActionCount()
        }
    }

    private val _objectCount = MutableStateFlow(0)
    val objectCount = _objectCount.asStateFlow()
    private val _serviceCount = MutableStateFlow(0)
    val serviceCount = _serviceCount.asStateFlow()

    fun loadActionCount() {
        viewModelScope.launch {
            val annonces = annonceRepository.getAnnoncesCurrentUser()
            _objectCount.value = annonces.count { it.action == "Objet" }
            _serviceCount.value = annonces.count { it.action == "Service" }
        }
    }

    //==============
    // MODIFY
    //==============


    var newUser by mutableStateOf<User>(
        User(
            name = "",
            email = "",
            phone = "",
            profilePictureUrl = "",
            inscriptionDate = 0L,
            neighborhoodName = ""
        )
    )

    var message by mutableStateOf("")

    fun changeUserData(oldPassword: String, newPassword: String?) {
        viewModelScope.launch {
            try {
                //securité
                authRepository.reauthenticate(oldPassword)

                if (!newPassword.isNullOrBlank()) {
                    authRepository.updatePassword(newPassword)
                }

                authRepository.reauthenticate(newPassword ?: oldPassword) //important sinon bug
                userRepository.updateUser(newUser)

                message = "Modification effectuée"

            } catch (e: Exception) {
                Log.e("CHANGE_USER", "Erreur", e)
                message = e.message ?: "Erreur inconnue"
            }
        }
    }



    fun updateName(newName: String) {
        newUser = newUser.copy(name = newName)
    }
    fun updateEmail(newEmail: String) {
        newUser = newUser.copy(email = newEmail)
    }
    fun updatePhone(newPhone: String) {
        newUser = newUser.copy(phone = newPhone)
    }
    fun updateProfilePicture(newUrl: String) {
        newUser = newUser.copy(profilePictureUrl = newUrl)
    }
    fun updateNeighborhood(newNeighborhood: String) {
        newUser = newUser.copy(neighborhoodName = newNeighborhood)
    }
    fun updateBio(newBio: String) {
        newUser = newUser.copy(bio = newBio)
    }


}