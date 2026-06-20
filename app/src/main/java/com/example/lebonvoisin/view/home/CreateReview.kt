package com.example.lebonvoisin.view.review

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.lebonvoisin.viewmodel.review.ReviewViewModel

/*
@Composable
fun CreateReview(navController: NavHostController,
                 sellerId: String){
    Text(text = sellerId.toString())
}
*/


@Composable
fun CreateReview(
    navController: NavHostController,
    sellerId: String
) {

    val viewModel: ReviewViewModel = hiltViewModel()

    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Laisser un avis",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Note du vendeur",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // ⭐ Rating stars
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..5) {
                    IconButton(onClick = { rating = i }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star $i",
                            tint = if (i <= rating)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }

            Text(
                text = "Titre de l'avis",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Ex: Très bon vendeur") }
            )

            Text(
                text = "Commentaire",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                label = { Text("Votre avis...") }
            )

            Button(
                onClick = {
                    viewModel.createReview(
                        sellerId = sellerId,
                        rating = rating,
                        title = title,
                        comment = comment
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = rating > 0 && comment.isNotBlank()
            ) {
                Text("Publier l'avis")
            }

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Annuler")
            }

            if (viewModel.message.isNotBlank()) {
                Text(
                    text = viewModel.message,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

