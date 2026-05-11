package com.example.lebonvoisin

import android.content.Context

import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign


class Post {

    val texte = ""
    val personne = Personne()

    @Composable
    fun apparaitre(message : String) {
        Box(

            modifier = Modifier.fillMaxHeight(),

            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "post de $message"
            )
        }
    }

    @Composable
    fun recupere(context: Context) {
        val bd = BaseDeDonnee(context)
        val donnee = bd.recupere_de_la_base()

        for (element in donnee) {
            apparaitre(element)
            println(element)
        }
    }
}