package com.example.lebonvoisin.view.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.lebonvoisin.dataclass.User
import com.example.lebonvoisin.viewmodel.profile.ProfileViewModel

@Composable
fun Modify_Profile(navController: NavHostController) {

    val viewModel: ProfileViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.loadUserInfo()
    }
    val user : User = viewModel.newUser

    var newPassword by remember { mutableStateOf("") }
    var oldPassword by remember { mutableStateOf("") }
    var changePassword by remember { mutableStateOf(false) }



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
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {

            Text(
                text = "Modifier le profil",
                style = MaterialTheme.typography.headlineMedium
            )


            Text(
                text = "Informations personnelles",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
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


            OutlinedTextField(
                value = user.neighborhoodName ?: "",
                onValueChange = { viewModel.updateNeighborhood(it) },
                label = { Text("Quartier") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = user.bio ?: "",
                onValueChange = { viewModel.updateBio(it) },
                label = { Text("Bio") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )



            Text(
                text = "Sécurité",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )


            OutlinedTextField(
                value = oldPassword,
                onValueChange = { oldPassword = it },
                label = {
                    Text("Ancien mot de passe *")
                },
                supportingText = {
                    Text("Obligatoire pour enregistrer les modifications")
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = oldPassword.isBlank()
            )


            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = changePassword,
                            onClick = {
                                changePassword = !changePassword
                            }
                        )

                        Text(
                            text = "Changer mon mot de passe",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }


                    if(changePassword){

                        Text(
                            text = "Nouveau mot de passe",
                            style = MaterialTheme.typography.titleSmall
                        )


                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = {
                                Text("Nouveau mot de passe")
                            },
                            supportingText = {
                                Text("Minimum recommandé : 8 caractères")
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }
            }



            Button(
                onClick = {
                    if(changePassword){
                        viewModel.changeUserData(
                            oldPassword,
                            newPassword
                        )
                    }else{
                        viewModel.changeUserData(
                            oldPassword,
                            null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enregistrer les modifications")
            }


            OutlinedButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Revenir au profil")
            }


            if (viewModel.message.isNotBlank()) {
                Text(
                    text = viewModel.message,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}





