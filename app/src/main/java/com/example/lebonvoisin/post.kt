package com.example.lebonvoisin

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class Post {

    val texte = ""
    val personne = Personne()

    @Composable
    fun apparaitre(message : String) {
        Text(
            text = "post de $message"
        )
    }

    @Composable
    fun recupere(context: Context) {
        val bd = BaseDeDonnee(context)
        val donnee = bd.recupere_de_la_base()

        for (element in donnee) {
            apparaitre(element)
        }
    }
}