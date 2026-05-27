package com.example.lebonvoisin.viewmodel.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lebonvoisin.dataclass.Annonce
import com.example.lebonvoisin.dataclass.User
import com.example.lebonvoisin.repository.AnnonceRepository
import com.example.lebonvoisin.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val annonceRepository: AnnonceRepository
) : ViewModel() {

    //=================
    // User Info
    //=================

    var user by mutableStateOf<User?>(null)
    val userID by lazy { userRepository.getCurrentUserID() }

    fun loadUserInfo() {
        viewModelScope.launch {
            user = userRepository.getCurrentUser()
        }
    }

}