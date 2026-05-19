package com.example.lebonvoisin.view.annonces

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lebonvoisin.dataclass.Annonce
import com.example.lebonvoisin.viewmodel.annonces.MesAnnoncesViewModel
import kotlinx.coroutines.delay

@Composable
fun MesAnnonces(
    modifier: Modifier = Modifier,
) {
    val viewModel: MesAnnoncesViewModel = hiltViewModel()
    val annonces = viewModel.annonces
    val afficherAjouter = viewModel.afficherAjouter
    //Pop up message retour ajout
    var showMessage by remember { mutableStateOf(false) }

    LaunchedEffect(showMessage) {
        if (showMessage) {
            delay(5000)
            showMessage = false
        }
    }

    if (afficherAjouter) {
        AjouterAnnonce(
            onPublier = { nouvelle ->
                viewModel.ajouterAnnonce(nouvelle)
                showMessage = true
            },
            onBack = { viewModel.afficherAjouter = false }
        )
    } else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { viewModel.afficherAjouter = true }) {
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
                TopMessage(message = viewModel.successMessage, visible = showMessage)

                if (showMessage) {
                    Spacer(Modifier.height(16.dp))
                }

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


@Composable
fun TopMessage(message: String, visible: Boolean) {
    AnimatedVisibility(
        visible = visible
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFC8E6C9) // Vert clair pour le fond
                )
            ) {
                Text(
                    text = message,
                    color = Color(0xFF4CAF50), // Vert pour succès
                    modifier = Modifier.padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}