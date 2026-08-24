package com.example.animeapp.screens.allanime

// Importer for layout, listevisning og tekstfelt
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Felles komponenter
import com.example.animeapp.components.AnimeCard
import com.example.animeapp.components.ErrorMessage
import com.example.animeapp.components.LoadingIndicator

// ------------------------------------------------------
// AnimeListScreen
// Skjerm 1: viser en liste med populære anime fra API-et.
// Har filterfelt for tittel, loading og feilmelding.
// ------------------------------------------------------
@Composable
fun AnimeListScreen(
    viewModel: AnimeListViewModel
) {
    val state = viewModel.uiState

    // Lokalt filtertekst (koblet til UiState via ViewModel)
    var filterText by rememberSaveable { mutableStateOf("") }

    // Ytre layout for skjermen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 80.dp)
    ) {
        // Overskrift
        Text(
            text = "Anime Explorer",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold
        )

        // Kort forklaring
        Text(
            text = "Bla gjennom populære anime og filtrer etter tittel.",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Filter-felt for tittel
        OutlinedTextField(
            value = filterText,
            onValueChange = {
                filterText = it
                viewModel.onFilterTextChange(it)
            },
            label = { Text("Filtrer på tittel") },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Viser innhold basert på UiState
        when {
            state.isLoading -> {
                // Viser spinner mens vi laster
                LoadingIndicator()
            }

            state.errorMessage != null -> {
                // Viser feilmelding hvis noe gikk galt
                ErrorMessage(message = state.errorMessage)
            }

            else -> {
                // Filtrerer listen etter tittel (case-insensitive)
                val filteredList = state.animeList.filter { anime ->
                    val title = anime.title ?: ""
                    title.contains(filterText, ignoreCase = true)
                }

                if (filteredList.isEmpty()) {
                    // Tom visning hvis ingen matcher filteret
                    Text(
                        text = "Ingen anime matcher filteret.",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                } else {
                    // Liste med kort for hver anime
                    LazyColumn {
                        items(filteredList) { anime ->
                            AnimeCard(
                                title = anime.title ?: "Ukjent tittel",
                                imageUrl = anime.images?.jpg?.imageUrl,
                                score = anime.score,
                                popularity = anime.popularity,
                                episodes = anime.episodes,
                                synopsis = anime.synopsis ?: "Ingen beskrivelse"
                            )
                        }
                    }
                }
            }
        }
    }
}
