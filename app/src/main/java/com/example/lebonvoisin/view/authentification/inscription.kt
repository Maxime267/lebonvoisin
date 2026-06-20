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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation





@Composable
fun inscription(navController: NavHostController) {

    val viewModel: AuthViewModel = hiltViewModel()
    val user: User = viewModel.user.value

    var password by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

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

                //TODO verification email (maybe)
                OutlinedTextField(
                    value = user.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                //TODO password confirmation secure
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


                //TODO format phone number
                OutlinedTextField(
                    value = user.phone.toString(),
                    onValueChange = { viewModel.updatePhone(it) },
                    label = { Text("Téléphone") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                //TODO profile picture

                //TODO get neighboorhood from geolocation
                OutlinedTextField(
                    value = user.neighborhoodName ?: "",
                    onValueChange = { viewModel.updateNeighborhood(it) },
                    label = { Text("Quartier") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

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






















/*


//TODO version plus complète
//TODO verifier champ rempli
//version juste de test
@Composable
fun inscription(navController : NavHostController) {

    val viewModel: AuthViewModel = hiltViewModel()
    val user: User = viewModel.user.value
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope() // scope pour lancer les appels suspend de viewModel

    Column(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = user?.email ?: "",
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("password") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = user.name,
            onValueChange = { viewModel.updateName(it) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = user.phone.toString(),
            onValueChange = { viewModel.updatePhone(it) },
            label = { Text("phone") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = user.neighborhoodName ?: "",
            onValueChange = { viewModel.updateNeighborhood(it) },
            label = { Text("neighboorhood") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { scope.launch { viewModel.inscription(password) } }) {
            Text("S'inscrire")
        }

        Button(onClick = { navController.navigate("connection") }) {
            Text("Se connecter")
        }

        //Message d'erreur
        Text(text = viewModel.message.value)

    }





}
*/