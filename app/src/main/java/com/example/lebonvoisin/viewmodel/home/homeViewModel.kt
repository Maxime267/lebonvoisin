package com.example.lebonvoisin.viewmodel.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lebonvoisin.dataclass.Annonce
import com.example.lebonvoisin.repository.AnnonceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class homeViewModel @Inject constructor(
    private val repository: AnnonceRepository
) : ViewModel() {
    var annonces by mutableStateOf(listOf<Annonce>())
    var isLoading by mutableStateOf(false)

    fun chargerAnnonces() {
        isLoading = true


        viewModelScope.launch {
            annonces = repository.getAnnoncesAll()
            isLoading = false
        }
    }

//    fun chargerAnnonces() {
//        isLoading = true
//
//        repository.lire { result ->
//            annonces = result
//            isLoading = false
//        }
//    }


}