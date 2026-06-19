package com.example.lebonvoisin.view.home

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lebonvoisin.dataclass.Annonce
import com.example.lebonvoisin.viewmodel.home.homeViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import com.example.lebonvoisin.viewmodel.message.MessageViewModel


@Composable
fun HomeScreen(
    viewModel: homeViewModel = hiltViewModel(),
    messageViewModel: MessageViewModel = hiltViewModel()
) {
    val annonces = viewModel.annonces
    val isLoading = viewModel.isLoading
    var filtreSelectionne by remember { mutableStateOf("all") }

    val annoncesFiltrees = when (filtreSelectionne) {
        "Service" -> annonces.filter { it.action == "Service" }
        "Objet" -> annonces.filter { it.action == "Objet" }
        else -> annonces
    }
    var annonceSelectionnee by remember { mutableStateOf<Annonce?>(null) }

    LaunchedEffect(Unit) {
        viewModel.chargerAnnonces()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7FA))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF050826)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    FilterButton("Tout", filtreSelectionne == "all") {
                        filtreSelectionne = "all"
                    }

                    FilterButton("Services", filtreSelectionne == "Service") {
                        filtreSelectionne = "Service"
                    }

                    FilterButton("Objets", filtreSelectionne == "Objet") {
                        filtreSelectionne = "Objet"
                    }
                }
            }
        }

        if (isLoading) {
            Text(
                text = "Chargement...",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(annoncesFiltrees) { annonce ->
                    AnnonceCard(
                        annonce = annonce,
                        onClick = { annonceSelectionnee = annonce }
                    )
                }
            }
        }
    }
    annonceSelectionnee?.let { annonce ->
        AlertDialog(
            onDismissRequest = { annonceSelectionnee = null },
            title = {
                Text(text = annonce.titre)
            },
            text = {
                Column {
                    Text(text = annonce.description)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Type : ${annonce.typeService}")
                    Text(text = "Adresse : ${annonce.rue}")
                    Text(text = "Publié par : ${annonce.personne}")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        messageViewModel.contacterAnnonce(
                            annonce = annonce,
                            contenu = "Bonjour, je suis intéressé par votre annonce."
                        )

                        annonceSelectionnee = null
                    }
                ) {
                    Text("Contacter / Demander")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { annonceSelectionnee = null }
                ) {
                    Text("Fermer")
                }
            }
        )
    }
}

@Composable
fun AnnonceCard(annonce: Annonce, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
            Text(
                text = when (annonce.typeService) {
                    "Bricolage" -> "🔨"
                    "Jardinage" -> "🌱"
                    "Garde d'animaux" -> "🐶"
                    "Cours particuliers" -> "📚"
                    "Transport" -> "🚗"
                    "Autre" -> "✨"
                    else -> "📦"
                },
                fontSize = 32.sp,
                modifier = Modifier.padding(end = 14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = annonce.titre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = annonce.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = annonce.typeService,
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))
                Row(

                ) {
                    Text(
                        text = annonce.personne,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(80.dp))
                    Text(
                        text = annonce.rue,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }

            }

            Text(
                text = if (annonce.action == "Objet") "Objet" else "Service",
                color = if (annonce.action == "Objet")
                    Color(0xFF2E9B4D)
                else
                    Color(0xFF446DDB),
                modifier = Modifier
                    .background(
                        color = if (annonce.action == "Objet")
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

@Composable
fun FilterButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFF050826) else Color(0xFFEDEEF2),
            contentColor = if (selected) Color.White else Color(0xFF1B1B2F)
        )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold
        )
    }
}