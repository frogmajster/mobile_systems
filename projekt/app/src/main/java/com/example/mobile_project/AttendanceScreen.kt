package com.example.mobile_project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ClubEvent(
    val id: Int,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val isConfirmed: Boolean = false
)

@Composable
fun AttendanceScreen() {
    var events by remember {
        mutableStateOf(
            listOf(
                ClubEvent(1, "Spotkanie organizacyjne", "25 Października 2023", "18:00", "Sala 213"),
                ClubEvent(2, "Warsztaty: Jetpack Compose", "28 Października 2023", "17:30", "Lab 4"),
                ClubEvent(3, "Hackathon Koła DNS", "5 Listopada 2023", "09:00", "Aula Główna")
            )
        )
    }

    var showQrDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showQrDialog = true },
                icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Skanuj QR") },
                text = { Text("Skanuj QR") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Nadchodzące wydarzenia",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(events) { event ->
                    EventCard(
                        event = event,
                        onConfirm = {
                            events = events.map {
                                if (it.id == event.id) it.copy(isConfirmed = true) else it
                            }
                        }
                    )
                }
            }
        }
    }

    // Modalny dialog symulujący skaner QR
    if (showQrDialog) {
        AlertDialog(
            onDismissRequest = { showQrDialog = false },
            icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = null, modifier = Modifier.size(48.dp)) },
            title = { Text("Skaner QR") },
            text = { Text("W tym miejscu uruchomiłby się aparat urządzenia w celu zeskanowania kodu QR (wyświetlonego np. przez prezesa koła), co automatycznie zweryfikowałoby Twoją obecność.") },
            confirmButton = {
                TextButton(onClick = { showQrDialog = false }) {
                    Text("Rozumiem")
                }
            }
        )
    }
}

@Composable
fun EventCard(event: ClubEvent, onConfirm: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = event.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CalendarToday, contentDescription = "Data", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = event.date, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AccessTime, contentDescription = "Czas", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = event.time, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = "Miejsce", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = event.location, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (event.isConfirmed) {
                // Informacja o potwierdzonym udziale
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Potwierdzono", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Udział potwierdzony",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                // Przycisk potwierdzający chęć udziału
                Button(
                    onClick = onConfirm,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Potwierdź chęć udziału", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
