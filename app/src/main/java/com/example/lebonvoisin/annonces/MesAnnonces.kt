package com.example.lebonvoisin.annonces

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MesAnnonces(modifier: Modifier = Modifier) {

    var annonces by remember { mutableStateOf(listOf<Annonce>()) }
    var afficherAjouter by remember { mutableStateOf(false) }

    if (afficherAjouter) {
        AjouterAnnonce(
            onPublier = { nouvelle ->
                annonces = annonces + nouvelle
                afficherAjouter = false
            },
            onBack = { afficherAjouter = false }
        )
    } else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { afficherAjouter = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Ajouter une annonce")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Mes annonces",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                if (annonces.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Aucune annonce.\nAppuyez sur + pour en créer une !",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(annonces) { annonce ->
                            AnnonceCard(annonce)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnnonceCard(annonce: Annonce) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(annonce.titre, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(Modifier.height(4.dp))
            Text(
                annonce.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
            Spacer(Modifier.height(6.dp))
            AssistChip(onClick = {}, label = { Text(annonce.typeService, fontSize = 12.sp) })
        }
    }
}