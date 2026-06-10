package com.example.lebonvoisin.view.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lebonvoisin.dataclass.User
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel
import com.example.lebonvoisin.viewmodel.profile.ProfileViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Profile(modifier: Modifier = Modifier) {

    val authViewModel : AuthViewModel = hiltViewModel()
    val profileViewModel : ProfileViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        profileViewModel.loadUserInfo()
    }
    val user : User? = profileViewModel.user

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Header profil

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Voisinage : " + user?.neighborhoodName ) // TODO DB
            Text(text = "Membre depuis le " + user?.inscriptionDate) // TODO DB


        // Note

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Note",
                        tint = Color(0xFFFFC107)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("4.8 / 5") // TODO DB
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("(32 avis)") // TODO DB
                }
            }


        // Services / box

            service_box()


        // Annonces

            Text(
                text = "Mes annonces",
                style = MaterialTheme.typography.titleMedium
            )


        // Actions

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                Text(text = profileViewModel.userID.toString())

                Button(
                    onClick = {
                        profileViewModel.loadUserInfo() //TODO Change juste c un test
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Modifier profil")
                }

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Paramètres")
                }

                Button(
                    onClick = {authViewModel.signOut() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Se déconnecter", color = Color.White)
                }
            }

    }
}


@Composable
fun service_box(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        service_box_creation(Icons.Default.ShoppingCart, 0, "Objets échangés")
        service_box_creation(Icons.Default.Face, 0 , "Services échangés")
    }
}

@Composable
fun service_box_creation(varIcon : ImageVector, nb_echanger : Int, title: String){
    Card(
        modifier = Modifier.size(150.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Icon(
                    imageVector = varIcon,
                    contentDescription = title,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(title)
                Text(nb_echanger.toString()) //TODO db
            }
        }
    }
}