package com.example.lebonvoisin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lebonvoisin.ui.theme.LebonvoisinTheme
import com.example.lebonvoisin.Post
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = FirebaseFirestore.getInstance()

        val voisin = hashMapOf(
            "nom" to "Rayan",
            "service" to "Jardinage",
            "ville" to "Paris"
        )

        db.collection("voisins")
            .add(voisin)
            .addOnSuccessListener {
                println("Ajout réussi")
            }
            .addOnFailureListener {
                println("Erreur")
            }
        enableEdgeToEdge()
        setContent {
            LebonvoisinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )


                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LebonvoisinTheme {
        Greeting("Android")
    }
}