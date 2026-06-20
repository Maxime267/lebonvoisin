package com.example.lebonvoisin.view.authentification

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.lebonvoisin.dataclass.User
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.lebonvoisin.location.LocationHelper


@Composable
fun inscription(navController: NavHostController) {

    val viewModel: AuthViewModel = hiltViewModel()
    val user: User = viewModel.user.value

    var password by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val locationHelper = remember { LocationHelper(context) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Inscription",
                    style = MaterialTheme.typography.headlineMedium
                )

                OutlinedTextField(
                    value = user.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Mot de passe") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = user.name,
                    onValueChange = { viewModel.updateName(it) },
                    label = { Text("Nom") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )


                OutlinedTextField(
                    value = user.phone.toString(),
                    onValueChange = { viewModel.updatePhone(it) },
                    label = { Text("Téléphone") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                /*
                OutlinedTextField(
                    value = user.neighborhoodName ?: "",
                    onValueChange = { viewModel.updateNeighborhood(it) },
                    label = { Text("Nom de la Rue") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )*/

                OutlinedTextField(
                    value = user.neighborhoodName ?: "",
                    onValueChange = { viewModel.updateNeighborhood(it) },
                    label = { Text("Voisinage") },
                    placeholder = { Text("Décrivez votre service…") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        locationHelper.getCurrentAddress { adresse ->
                            viewModel.updateNeighborhood(adresse)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("📍 Utiliser ma position actuelle")
                }


                Button(
                    onClick = {
                        scope.launch {
                            viewModel.inscription(password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("S'inscrire")
                }

                OutlinedButton(
                    onClick = {
                        navController.navigate("connection")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Déjà un compte ? Se connecter")
                }

                if (viewModel.message.value.isNotBlank()) {
                    Text(
                        text = viewModel.message.value,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}