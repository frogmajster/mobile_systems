package com.example.mobile_project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Model danych reprezentujący zespół
data class Team(
    val id: Int,
    val name: String,
    val description: String,
    val membersCount: Int,
    val maxMembers: Int,
    val tags: List<String>
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ZespolyScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf<String?>(null) }

    // Przykładowe dane zespołów koła DNS
    val teams = listOf(
        Team(1, "Aplikacje Mobilne", "Tworzymy nowoczesne aplikacje na Androida i iOS.", 5, 8, listOf("Kotlin", "Swift", "Jetpack Compose")),
        Team(2, "Sztuczna Inteligencja", "Badania i rozwój w zakresie uczenia maszynowego.", 10, 10, listOf("Python", "TensorFlow", "PyTorch")),
        Team(3, "Game Development", "Tworzenie gier w środowisku Unity i Unreal Engine.", 4, 10, listOf("C#", "C++", "Unity")),
        Team(4, "Web Development", "Projekty typu Full-stack dla kół naukowych.", 7, 12, listOf("React", "TypeScript", "Node.js")),
        Team(5, "Cyberbezpieczeństwo", "Analiza zabezpieczeń i testy penetracyjne.", 3, 5, listOf("Python", "Bash", "Linux"))
    )

    // Unikalne tagi ze wszystkich zespołów posortowane alfabetycznie
    val allTags = remember(teams) {
        teams.flatMap { it.tags }.distinct().sorted()
    }

    // Filtrowanie listy po nazwie zespołu lub tagach
    val filteredTeams = teams.filter { team ->
        val matchesSearch = team.name.contains(searchQuery, ignoreCase = true) ||
                            team.tags.any { tag -> tag.contains(searchQuery, ignoreCase = true) }
        val matchesTag = selectedTag == null || team.tags.contains(selectedTag)

        matchesSearch && matchesTag
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Wyszukiwarka
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Szukaj zespołu lub technologii (np. Python)...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Szukaj") },
            shape = MaterialTheme.shapes.extraLarge,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Poziome menu ze wszystkimi tagami
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(allTags) { tag ->
                FilterChip(
                    selected = tag == selectedTag,
                    onClick = {
                        // Kliknięcie tego samego odznacza tag
                        selectedTag = if (selectedTag == tag) null else tag
                    },
                    label = { Text(tag) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista zespołów
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredTeams) { team ->
                TeamCard(
                    team = team,
                    onTagClick = { clickedTag ->
                        selectedTag = if (selectedTag == clickedTag) null else clickedTag
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TeamCard(team: Team, onTagClick: (String) -> Unit) {
    val availableSpots = team.maxMembers - team.membersCount
    // Lokalny stan przycisku: false = domyślny, true = wysłano prośbę
    var isRequested by remember { mutableStateOf(false) }

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
            // Nazwa zespołu
            Text(
                text = team.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Opis
            Text(
                text = team.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Informacje o członkach
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Group,
                    contentDescription = "Członkowie",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${team.membersCount} / ${team.maxMembers} członków",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.weight(1f))

                // Ilość wolnych miejsc
                Text(
                    text = if (availableSpots > 0) "Wolnych: $availableSpots" else "Brak miejsc",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (availableSpots > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tagi technologii
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                team.tags.forEach { tag ->
                    SuggestionChip(
                        onClick = { onTagClick(tag) },
                        label = { Text(tag) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Przycisk "Dołącz / Oczekujące"
            Button(
                onClick = { isRequested = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = availableSpots > 0 && !isRequested, // Wyłącz jeśli brak miejsc lub już wysłano prośbę
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = when {
                        isRequested -> "Oczekiwanie na zaakceptowanie"
                        availableSpots > 0 -> "Wyślij prośbę o dołączenie"
                        else -> "Zespół pełny"
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}