package com.example.animeapp.screens.searchanimebyid

// Importer for layout, scroll, tekstfelt og loading
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// Felles komponenter
import com.example.animeapp.components.AnimeCard
import com.example.animeapp.components.ErrorMessage
import com.example.animeapp.components.PrimaryButton
import com.example.animeapp.components.SecondaryButton

// ------------------------------------------------------
// AnimeSearchScreen
// Skjerm 2: søk etter en anime ved å skrive inn ID (mal_id).
// Viser loading, feilmeldinger og resultat-kort.
// ------------------------------------------------------
@Composable
fun AnimeSearchScreen(
    viewModel: AnimeSearchViewModel,
    onBack: () -> Unit
) {
    val state = viewModel.uiState
    var idText by rememberSaveable { mutableStateOf("") }

    // Ytre layout + scroll
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 24.dp, bottom = 80.dp)
    ) {
        // Tittel
        Text(
            text = "Anime-søk",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        // Kort forklaring
        Text(
            text = "Skriv inn anime-id (mal_id) for å hente detaljer om en spesifikk anime.",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tilbake-knapp
        SecondaryButton(
            text = "Tilbake",
            onClick = onBack,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tekstfelt for ID
        OutlinedTextField(
            value = idText,
            onValueChange = { idText = it },
            label = { Text("Skriv inn anime-id (mal_id)") },
            singleLine = true,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Rad med Søk + Tøm
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrimaryButton(
                text = "Søk",
                onClick = { viewModel.search(idText) },
                modifier = Modifier.weight(1f)
            )
            SecondaryButton(
                text = "Tøm",
                onClick = {
                    idText = ""
                    viewModel.clear()
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hurtigknapper for sist vellykkede søk
        if (state.lastSuccessfulIds.isNotEmpty()) {
            Text(
                text = "Sist søkte ID-er:",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.lastSuccessfulIds.forEach { recentId ->
                    SecondaryButton(
                        text = recentId.toString(),
                        onClick = {
                            idText = recentId.toString()
                            viewModel.search(recentId.toString())
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Innhold basert på UiState
        when {
            // Loading
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // Feil
            state.error != null -> {
                ErrorMessage(
                    message = state.error,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }

            // Treff – viser kort med resultat
            state.result != null -> {
                Spacer(modifier = Modifier.height(8.dp))
                AnimeCard(
                    title = state.result.title ?: "Ukjent tittel",
                    imageUrl = state.result.images?.jpg?.imageUrl,
                    score = state.result.score,
                    popularity = state.result.popularity,
                    episodes = state.result.episodes,
                    synopsis = state.result.synopsis ?: "Ingen beskrivelse"
                )
            }

            // Start-/tom-tilstand
            else -> {
                Text(
                    text = "Skriv inn en id og trykk \"Søk\" for å hente en anime.",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
