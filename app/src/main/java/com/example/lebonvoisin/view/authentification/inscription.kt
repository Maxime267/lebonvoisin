package com.example.lebonvoisin.view.authentification

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.lebonvoisin.dataclass.User
import com.example.lebonvoisin.viewmodel.authentification.AuthViewModel
import kotlinx.coroutines.launch


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
        //TODO verification email (maybe)
        OutlinedTextField(
            value = user?.email ?: "",
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        //TODO password confirmation secure
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
        //TODO format phone number
        OutlinedTextField(
            value = user.phone.toString(),
            onValueChange = { viewModel.updatePhone(it) },
            label = { Text("phone") },
            modifier = Modifier.fillMaxWidth()
        )
        //TODO profile picture

        //TODO get neighboorhood from geolocation
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