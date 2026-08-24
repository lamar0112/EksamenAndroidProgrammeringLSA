package com.example.animeapp.screens.searchanimebyid

import com.example.animeapp.data.api.AnimeDto

// ------------------------------------------------------
// AnimeSearchUiState
// Holder tilstand for skjerm 2 (søk etter anime via ID).
// ------------------------------------------------------
data class AnimeSearchUiState(

    // Viser loading under søk
    val isLoading: Boolean = false,

    // Resultat fra API (null hvis ingenting funnet)
    val result: AnimeDto? = null,

    // Feilmelding (ugyldig id eller API-feil)
    val error: String? = null,

    // Liste over sist vellykkede søke-id-er
    val lastSuccessfulIds: List<Int> = emptyList()
)
