package com.example.lebonvoisin.viewmodel.annonces

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lebonvoisin.dataclass.Annonce
import com.example.lebonvoisin.repository.AnnonceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MesAnnoncesViewModel @Inject constructor(
    private val repository: AnnonceRepository
) : ViewModel() {

    var annonces by mutableStateOf(listOf<Annonce>())
    var afficherAjouter by mutableStateOf(false)

    fun ajouterAnnonce(nouvelle: Annonce) {
        annonces = annonces + nouvelle
        afficherAjouter = false
    }
}