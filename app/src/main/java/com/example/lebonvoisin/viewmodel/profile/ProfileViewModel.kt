package com.example.lebonvoisin.viewmodel.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lebonvoisin.dataclass.Annonce
import com.example.lebonvoisin.dataclass.User
import com.example.lebonvoisin.repository.AnnonceRepository
import com.example.lebonvoisin.repository.AuthRepository
import com.example.lebonvoisin.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
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