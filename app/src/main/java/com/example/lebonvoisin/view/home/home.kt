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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun HomeScreen(
    viewModel: homeViewModel = hiltViewModel()
) {
    val annonces = viewModel.annonces
    val isLoading = viewModel.isLoading

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
                items(annonces) { annonce ->
                    AnnonceCard(annonce)
                }
            }
        }
    }
}

@Composable
fun AnnonceCard(annonce: Annonce) {
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
            Text(
                text = if (annonce.typeService == "object") "🔧" else "🐶",
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
            }

            Text(
                text = if (annonce.typeService == "object") "Objet" else "Service",
                color = if (annonce.typeService == "object")
                    Color(0xFF2E9B4D)
                else
                    Color(0xFF446DDB),
                modifier = Modifier
                    .background(
                        color = if (annonce.typeService == "object")
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