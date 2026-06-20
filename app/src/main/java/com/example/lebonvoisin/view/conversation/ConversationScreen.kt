package com.example.lebonvoisin.view.message

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lebonvoisin.dataclass.Message
import com.example.lebonvoisin.viewmodel.message.MessageViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ConversationScreen(
    annonceId: String,
    otherUserId: String,
    onBackClick: () -> Unit,
    viewModel: MessageViewModel = hiltViewModel()
) {
    var texteMessage by remember { mutableStateOf("") }

    LaunchedEffect(annonceId, otherUserId) {
        viewModel.chargerConversationAvec(
            annonceId = annonceId,
            otherUserId = otherUserId
        )
    }

    val titreAnnonce = viewModel.messagesConversation.firstOrNull()?.annonceTitre ?: "Conversation"

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

            Text(
                text = titreAnnonce,
                style = MaterialTheme.typography.titleLarge
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.messagesConversation) { message ->
                MessageBubble(message = message)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = texteMessage,
                onValueChange = { texteMessage = it },
                placeholder = { Text("Écrire un message...") },
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    if (texteMessage.isNotBlank()) {
                        viewModel.envoyerMessageConversation(
                            annonceId = annonceId,
                            otherUserId = otherUserId,
                            contenu = texteMessage
                        )
                        texteMessage = ""
                    }
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    val isMine = message.senderId == currentUserId

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = if (isMine) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = message.contenu,
                modifier = Modifier.padding(12.dp),
                color = if (isMine) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}