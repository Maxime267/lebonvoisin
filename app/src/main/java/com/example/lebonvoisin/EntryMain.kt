package com.example.lebonvoisin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.lebonvoisin.core.DataBase
import com.example.lebonvoisin.navigation.AppNavGraph
import com.example.lebonvoisin.ui.theme.LebonvoisinTheme
import com.example.lebonvoisin.view.pAppBar.AppBar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryMain : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = DataBase()
        db.add()
        db.read { annonces ->

            for (annonce in annonces) {
                Log.d("TEST", annonce["nom"] ?: "Pas de nom")
            }

            println(annonces)
        enableEdgeToEdge()
        setContent {
            LebonvoisinTheme {
                //MainScreen()
                AccueilScreen(annonces)
            }
        }
    }
}
}



@Composable
fun AccueilScreen( annonces: List<Map<String, String>>){


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // Header du haut
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF050826)), // bleu très foncé
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Covoisinage",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Entraide entre voisins",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp
                )
            }
        }

        // LISTE DES ANNONCES
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(annonces) { annonce ->

                AnnonceCard(annonce)
            }
        }
    }
}

@Composable
fun AnnonceCard(annonce: Map<String, String>) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            // Emoji à gauche
            Text(
                text =
                    if (annonce["action"] == "object")
                        "🔧"
                    else
                        "🐶",
                fontSize = 32.sp,
                modifier = Modifier.padding(end = 14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                // Nom annonce
                Text(
                    text = annonce["nom"] ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Description
                Text(
                    text = annonce["description"] ?: "",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Personne
                Text(
                    text = "👤 ${annonce["personne"] ?: ""}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                // Rue
                Text(
                    text = "📍 ${annonce["rue"] ?: ""}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            // Badge Service / Objet
            Text(
                text =
                    if (annonce["action"] == "object")
                        "Objet"
                    else
                        "Service",

                color =
                    if (annonce["action"] == "object")
                        Color(0xFF2E9B4D)
                    else
                        Color(0xFF446DDB),

                modifier = Modifier
                    .background(
                        color =
                            if (annonce["action"] == "object")
                                Color(0xFFDDF5E5)
                            else
                                Color(0xFFE3EDFF),

                        shape = RoundedCornerShape(50.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }
    }
}


