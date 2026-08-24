package com.example.animeapp.screens.animeidea

// Importer for Room-entity
import com.example.animeapp.data.db.AnimeIdea

// ------------------------------------------------------
// AnimeIdeasUiState
// Holder tilstand for skjerm 3 (anime-ideer med Room).
// Brukes av ViewModel for å oppdatere UI.
// ------------------------------------------------------
data class AnimeIdeasUiState(
    // Tekst brukeren har skrevet inn i tittel-feltet
    val titleInput: String = "",

    // Tekst brukeren har skrevet inn i beskrivelse-feltet
    val descriptionInput: String = "",

    // Liste med ideer hentet fra databasen
    val ideas: List<AnimeIdea> = emptyList(),

    // True når vi laster ideer
    val isLoading: Boolean = false,

    // Feilmelding hvis noe går galt
    val errorMessage: String? = null
)
