package com.example.lebonvoisin.view.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lebonvoisin.dataclass.review.Review
import com.example.lebonvoisin.viewmodel.review.ReviewViewModel
import androidx.compose.foundation.lazy.items
import com.example.lebonvoisin.dataclass.review.ReviewUI

@Composable
fun SeeReview() {

    val viewModel: ReviewViewModel = hiltViewModel()
    viewModel.loadReviews()

    val reviewsOnMe = viewModel.reviewsOnMe
    val myReviews = viewModel.myReviews

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        item {
            Text("Les reviews sur mes posts")
        }

        if (reviewsOnMe.isEmpty()) {
            item {
                Text("Aucune review pour le moment")
            }
        } else {
            items(reviewsOnMe) { review ->
                ReviewCard(review)
            }
        }

        item {
            Text("Mes reviews")
        }

        if (myReviews.isEmpty()) {
            item {
                Text("Tu n’as encore laissé aucune review")
            }
        } else {
            items(myReviews) { review ->
                ReviewCard(review)
            }
        }
    }
}







@Composable
private fun ReviewCard(review : ReviewUI) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            AssistChip(
                onClick = {},
                label = { Text("Bénéficiaire: ${review.ownerName}", fontSize = 12.sp) }
            )

            // Titre + note
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = review.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )

                RatingStars(rating = review.rating)
            }

            Spacer(Modifier.height(6.dp))

            // Commentaire
            Text(
                text = review.comment,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
            )

            Spacer(Modifier.height(8.dp))

            // Infos seller / chip optionnel
            AssistChip(
                onClick = {},
                label = { Text("Offrant: ${review.sellerName}", fontSize = 12.sp) }
            )
        }
    }
}

@Composable
private fun RatingStars(rating: Int, max: Int = 5) {
    Row {
        for (i in 1..max) {
            Text(
                text = if (i <= rating) "★" else "☆",
                fontSize = 14.sp,
                color = if (i <= rating)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outline
            )
        }
    }
}