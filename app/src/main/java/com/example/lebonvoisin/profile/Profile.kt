package com.example.lebonvoisin.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun Profile(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 🔹 Avatar
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Localisation") //TODO db
        Text(text = "Membre depuis ...") //TODO db

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Note
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Note",
                tint = Color.Yellow
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Note/5") //TODO db
            Spacer(modifier = Modifier.width(4.dp))
            Text("(nb avis)") //TODO db
        }

        Spacer(modifier = Modifier.height(16.dp))


        //BOX
        service_box()

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Liste annonces
        Text("Mes annonces")

        LazyColumn(
            modifier = Modifier
                .weight(1f) // prend l'espace restant
                .fillMaxWidth()
        ) {
            items(5) { index ->
                Text("Annonce $index", modifier = Modifier.padding(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Boutons
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Modifier profil")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Paramètres")
        }
    }
}

