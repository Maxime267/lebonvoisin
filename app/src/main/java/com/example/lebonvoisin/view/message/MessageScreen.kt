package com.example.lebonvoisin.view.message

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.lebonvoisin.dataclass.Conversation
import com.example.lebonvoisin.viewmodel.message.MessageViewModel

@Composable
fun MessageScreen(
    navController: NavHostController,
    viewModel: MessageViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.chargerConversations()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F7FF))
            .padding(16.dp)
    ) {
        Text(
            text = "Messages",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(viewModel.conversations) { conversation ->
                ConversationCard(
                    conversation = conversation,
                    onClick = {
                        navController.navigate("conversation/${conversation.userId}")
                    }
                )
            }
        }
    }
}

@Composable
fun ConversationCard(
    conversation: Conversation,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE1E4F2)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = conversation.annonceTitre.firstOrNull()?.uppercase() ?: "?",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = conversation.annonceTitre,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = conversation.userName,
                    color = Color.Gray
                )

                Text(
                    text = conversation.dernierMessage.contenu,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )
            }

            if (conversation.nombreMessages > 1) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFDCE2FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = conversation.nombreMessages.toString(),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}