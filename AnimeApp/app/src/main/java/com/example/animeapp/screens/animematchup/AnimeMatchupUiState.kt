package com.example.animeapp.screens.animematchup

import com.example.animeapp.data.api.AnimeDto

// ------------------------------------------------------
// AnimeMatchupUiState
// Holder all tilstand for skjerm 4 (matchup-spillet).
// ------------------------------------------------------
data class AnimeMatchupUiState(

    // De to animeene brukeren kan velge mellom
    val anime1: AnimeDto? = null,
    val anime2: AnimeDto? = null,

    // Info om siste valg
    val lastChosenTitle: String? = null,
    val lastChosenPopularity: Int? = null,
    val lastChosenScore: Double? = null,
    val comparisonMessage: String? = null,
    val lastWasMostPopular: Boolean? = null,

    // Statistikk for spillet
    val totalRounds: Int = 0,
    val correctRounds: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,

    // Loading og feil
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
