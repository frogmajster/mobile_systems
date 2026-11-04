package com.example.mobile_project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(pendingTeamId: Int? = null) {
    // Określamy status zespołu - Domyślnie BRAK
    val (teamStatusText, statusColor) = when {
        pendingTeamId != null -> Pair("Oczekiwanie na dołączenie", MaterialTheme.colorScheme.tertiary)
        else -> Pair("Zespół: Brak", MaterialTheme.colorScheme.outline)
    }

    // Stan rozwinięcia szczegółów obecności
    var showAttendanceDetails by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Awatar użytkownika z opcją edycji
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Awatar",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // Przycisk edycji zdjęcia
            SmallFloatingActionButton(
                onClick = { /* TODO: Otwarcie galerii/aparatu */ },
                modifier = Modifier
                    .size(40.dp)
                    .offset(x = 8.dp, y = 8.dp),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Zmień zdjęcie")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Imię, Nazwisko i Email
        Text(
            text = "Jan Kowalski",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "257290@edu.p.lodz.pl",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Zespół w kole DNS (Brak, Oczekiwanie, Należy)
        Surface(
            color = statusColor.copy(alpha = 0.1f),
            shape = MaterialTheme.shapes.medium,
            border = androidx.compose.foundation.BorderStroke(1.dp, statusColor)
        ) {
            Text(
                text = teamStatusText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = statusColor,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Skonsolidowana sekcja Obecności
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .clickable { showAttendanceDetails = !showAttendanceDetails }, // Kliknięcie rozwija szczegóły
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Obecność: odbył 13 spotkań",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    // Ikonka strzałki zmieniająca kierunek w zależności od stanu
                    Icon(
                        imageVector = if (showAttendanceDetails) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Szczegóły obecności",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Animowane pojawienie się szczegółów po kliknięciu
                AnimatedVisibility(visible = showAttendanceDetails) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        Text(text = "• Wykłady: 7", fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "• Prelekcje: 4", fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "• Warsztaty: 2", fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sekcja Ustawień
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Ustawienia aplikacji",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SettingItem(icon = Icons.Default.Notifications, text = "Powiadomienia")
            SettingItem(icon = Icons.Default.Settings, text = "Motyw i wygląd")

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(8.dp))

            SettingItem(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                text = "Wyloguj",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun SettingItem(icon: ImageVector, text: String, color: Color = MaterialTheme.colorScheme.onSurface) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = color,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 18.sp,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}
