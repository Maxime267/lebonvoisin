package com.example.lebonvoisin.annonces

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    var titre by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var typeSelectionne by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    var titreError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }
    var typeError by remember { mutableStateOf(false) }

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
                onValueChange = { titre = it; titreError = false },
                label = { Text("Titre") },
                placeholder = { Text("Ex : Tonte de pelouse") },
                isError = titreError,
                supportingText = { if (titreError) Text("Champ obligatoire") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )


            OutlinedTextField(
                value = description,
                onValueChange = { description = it; descriptionError = false },
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
                                typeSelectionne = type
                                typeError = false
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = {
                    titreError = titre.isBlank()
                    descriptionError = description.isBlank()
                    typeError = typeSelectionne.isBlank()
                    if (!titreError && !descriptionError && !typeError) {
                        onPublier(
                            Annonce(
                                id = System.currentTimeMillis().toInt(),
                                titre = titre.trim(),
                                description = description.trim(),
                                typeService = typeSelectionne
                            )
                        )
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