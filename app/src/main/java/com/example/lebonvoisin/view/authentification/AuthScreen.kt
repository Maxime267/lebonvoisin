package com.example.lebonvoisin.view.authentification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(navController: NavHostController) {

    val viewModel: AuthViewModel = hiltViewModel()
    val user = viewModel.user.value

    var password by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
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
                    text = "Connexion",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 28.sp
                )

                OutlinedTextField(
                    value = user.email,
                    onValueChange = viewModel::updateEmail,
                    label = { Text("Adresse e-mail") },
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.connection(
                                    email = user.email,
                                    password = password
                                )
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Connexion")
                    }

                    OutlinedButton(
                        onClick = {
                            navController.navigate("inscription")
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Inscription")
                    }
                }

                if (viewModel.firebaseCurrentUser.value != null) {
                    Button(
                        onClick = { viewModel.signOut() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Se déconnecter")
                    }
                }

                if (viewModel.message.value.isNotBlank()) {
                    Text(
                        text = viewModel.message.value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}