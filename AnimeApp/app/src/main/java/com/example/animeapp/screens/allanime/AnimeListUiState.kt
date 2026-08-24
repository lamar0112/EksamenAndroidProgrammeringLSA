package com.example.animeapp.screens.allanime

import com.example.animeapp.data.api.AnimeDto

// ------------------------------------------------------
// AnimeListUiState
// Holder tilstand for skjerm 1 (anime-listen).
// Brukes av ViewModel for å oppdatere UI.
// ------------------------------------------------------
data class AnimeListUiState(
    // Viser loading mens API-et henter data
    val isLoading: Boolean = false,

    // Feilmelding hvis API-kall feiler
    val errorMessage: String? = null,

    // Listen som vises i UI
    val animeList: List<AnimeDto> = emptyList(),

    // Teksten i filter-feltet
    val filterText: String = ""
)
