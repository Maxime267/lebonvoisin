package com.example.lebonvoisin.viewmodel.annonces
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.lebonvoisin.dataclass.Annonce
import com.example.lebonvoisin.repository.AnnonceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AjouterAnnonceViewModel @Inject constructor(
    private val repository: AnnonceRepository //TODO ADD connection to firebase (publier annonce)
) : ViewModel() {


    //=================
    // Ajouter Annonce
    //=================

    var titre by mutableStateOf("")
    var description by mutableStateOf("")
    var typeSelectionne by mutableStateOf("")

    var titreError by mutableStateOf(false)
    var descriptionError by mutableStateOf(false)
    var typeError by mutableStateOf(false)

    fun publierAnnonce(onSuccess: (Annonce) -> Unit) {

        titreError = titre.isBlank()
        descriptionError = description.isBlank()
        typeError = typeSelectionne.isBlank()

        if (titreError || descriptionError || typeError) return

        val annonce = Annonce(
            id = System.currentTimeMillis().toInt(),
            titre = titre.trim(),
            description = description.trim(),
            typeService = typeSelectionne
        )

        repository.publier(
            annonce = annonce,
            onSuccess = {
                onSuccess(annonce)
                reset()
            },
            onFailure = {
                // ici tu peux ajouter un message d'erreur plus tard
            }
        )
    }

    fun reset() {
        titre = ""
        description = ""
        typeSelectionne = ""

        titreError = false
        descriptionError = false
        typeError = false
    }

    fun onTitreChange(it: String) {
        titre = it; titreError = false
    }

    fun onDescritpionChange(it: String){
        description = it; descriptionError = false
    }

    fun onTypeChange(type : String) {
        typeSelectionne = type
        typeError = false

    }
}
