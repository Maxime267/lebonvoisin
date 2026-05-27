package com.example.lebonvoisin.view.annonces

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lebonvoisin.dataclass.Annonce
import com.example.lebonvoisin.viewmodel.annonces.MesAnnoncesViewModel
import kotlinx.coroutines.launch

private val typesDeService = listOf(
    "Bricolage", "Jardinage", "Garde d'animaux",
    "Cours particuliers", "Transport", "Autre"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjouterAnnonce(
    onPublier: (Annonce) -> Unit,
    onBack: () -> Unit
) {
    val viewModel: MesAnnoncesViewModel = hiltViewModel()
    val titre = viewModel.titre
    val description = viewModel.description
    val typeSelectionne = viewModel.typeSelectionne

    val titreError = viewModel.titreError
    val descriptionError = viewModel.descriptionError
    val typeError = viewModel.typeError
    val scope = rememberCoroutineScope()


    var dropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nouvelle annonce") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            OutlinedTextField(
                value = titre,
                onValueChange = { viewModel.onTitreChange(it) },
                label = { Text("Titre") },
                placeholder = { Text("Ex : Tonte de pelouse") },
                isError = titreError,
                supportingText = { if (titreError) Text("Champ obligatoire") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )


            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.onDescritpionChange(it)},
                label = { Text("Description") },
                placeholder = { Text("Décrivez votre service…") },
                isError = descriptionError,
                supportingText = { if (descriptionError) Text("Champ obligatoire") },
                minLines = 4,
                maxLines = 6,
                modifier = Modifier.fillMaxWidth()
            )


            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = !dropdownExpanded }
            ) {
                OutlinedTextField(
                    value = typeSelectionne,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Type de service") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                    isError = typeError,
                    supportingText = { if (typeError) Text("Champ obligatoire") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    typesDeService.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                viewModel.onTypeChange(type)
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = {
                    scope.launch {
                        viewModel.publierAnnonce(onPublier)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Publier", fontSize = 16.sp)
            }
        }
    }
}