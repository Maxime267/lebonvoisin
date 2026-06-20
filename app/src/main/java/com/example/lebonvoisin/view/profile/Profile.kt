package com.example.lebonvoisin.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lebonvoisin.dataclass.User
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel
import com.example.lebonvoisin.viewmodel.profile.ProfileViewModel
import com.example.lebonvoisin.viewmodel.review.ReviewViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val reviewViewModel : ReviewViewModel = hiltViewModel()

    reviewViewModel.loadReviews()
    LaunchedEffect(Unit) {
        profileViewModel.loadUserInfo()
    }

    val user: User? = profileViewModel.user
    val colorScheme = MaterialTheme.colorScheme

    val userName = user?.name ?: "Utilisateur"
    val initial = userName.firstOrNull()?.uppercase() ?: "?"

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(Color(0xFFE1E4F2)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initial,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF30323D)
            )
        }

        Text(
            text = userName,
            style = MaterialTheme.typography.titleLarge,
            color = colorScheme.onBackground
        )

        Text(
            text = "Voisinage : ${user?.neighborhoodName ?: "Inconnu"}",
            color = colorScheme.onBackground.copy(alpha = 0.7f)
        )

        val date = Date(user?.inscriptionDate ?: 0L)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)

        Text(
            text = "Membre depuis le ${format.format(date)}",
            color = colorScheme.onBackground.copy(alpha = 0.5f),
            style = MaterialTheme.typography.bodySmall
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceContainerHighest),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Note",
                    tint = Color(0xFFFFC107)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = String.format("%.2f", reviewViewModel.avgReview()) + " / 5" , color = Color(0xFFFFC107), style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.width(6.dp))

                Text(text = "(" + reviewViewModel.reviewsOnMe.size.toString() + " avis)", color = colorScheme.onSurface.copy(alpha = 0.6f))

                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(
                    onClick = { navController.navigate("see_review") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Review"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Review")
                }
            }
        }

        service_box(profileViewModel = profileViewModel)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surfaceContainerHigh
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Bio",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorScheme.primary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = user?.bio ?: "Aucune bio renseignée.",
                    color = colorScheme.onSurface.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = { navController.navigate("modify") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Modifier profil")
            }

            Button(
                onClick = { authViewModel.signOut() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text("Se déconnecter", color = colorScheme.onError)
            }
        }
    }
}

@Composable
fun service_box(profileViewModel: ProfileViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        service_box_creation(
            varIcon = Icons.Default.ShoppingCart,
            nb_echanger = profileViewModel.objectCount.collectAsState().value,
            title = "Post d'Objets",
            modifier = Modifier.weight(1f)
        )

        service_box_creation(
            varIcon = Icons.Default.Face,
            nb_echanger = profileViewModel.serviceCount.collectAsState().value,
            title = "Post de Services",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun service_box_creation(
    varIcon: ImageVector,
    nb_echanger: Int,
    title: String,
    modifier: Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = modifier.height(140.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainerHigh
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = varIcon,
                    contentDescription = title,
                    modifier = Modifier.size(36.dp),
                    tint = colorScheme.primary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = title,
                    color = colorScheme.onSurface
                )

                Text(
                    text = nb_echanger.toString(),
                    color = colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}