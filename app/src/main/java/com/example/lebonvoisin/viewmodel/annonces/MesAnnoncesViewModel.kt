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


    //=================
    // Annonces
    //=================


    var annonces by mutableStateOf(listOf<Annonce>())
    var afficherAjouter by mutableStateOf(false)

    fun ajouterAnnonce(nouvelle: Annonce) {
        annonces = annonces + nouvelle
        afficherAjouter = false
    }






    //=================
    // Ajouter Annonce
    //=================

    var titre by mutableStateOf("")
    var description by mutableStateOf("")
    var typeSelectionne by mutableStateOf("")

    var action by mutableStateOf("")

    var rue by mutableStateOf("")

    var personne by mutableStateOf("")



    var titreError by mutableStateOf(false)
    var descriptionError by mutableStateOf(false)
    var typeError by mutableStateOf(false)

    var personneError by mutableStateOf(false)

    var rueError by mutableStateOf(false)

    var actionError by mutableStateOf(false)


    var successMessage by mutableStateOf("No message")

    suspend fun publierAnnonce(onSuccess: (Annonce) -> Unit) {

        titreError = titre.isBlank()
        descriptionError = description.isBlank()
        typeError = typeSelectionne.isBlank()
        personneError = personne.isBlank()
        rueError = rue.isBlank()
        actionError = action.isBlank()

        if (titreError || descriptionError || typeError) return

        val annonce = Annonce(
            id = System.currentTimeMillis().toInt(),
            titre = titre.trim(),
            description = description.trim(),
            typeService = typeSelectionne,
            action = action.trim(),
            personne = personne.trim(),
            rue = rue.trim()
        )
        successMessage = repository.publier(annonce)
        onSuccess(annonce)
        reset()
    }

    fun reset() {
        titre = ""
        description = ""
        typeSelectionne = ""
        action = ""
        rue = ""
        personne = ""

        titreError = false
        descriptionError = false
        typeError = false
        personneError = false
        rueError = false
        actionError = false
    }

    fun onTitreChange(it: String) {
        titre = it; titreError = false
    }

    fun onPersonneChange(it: String) {
        personne = it; personneError = false
    }

    fun onRueChange(it: String) {
        rue = it; rueError = false
    }

    fun onActionChange(it: String) {
        action = it; actionError = false
    }




    fun onDescritpionChange(it: String){
        description = it; descriptionError = false
    }

    fun onTypeChange(type : String) {
        typeSelectionne = type
        typeError = false

    }

    var localisation by mutableStateOf("")
        private set

    fun onLocationChange(newLocation: String) {
        localisation = newLocation
    }
}