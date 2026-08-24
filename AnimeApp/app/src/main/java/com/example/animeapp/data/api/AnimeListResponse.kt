package com.example.animeapp.data.api

// ------------------------------------------------------
// AnimeListResponse
// Brukes når vi henter en liste med anime (GET /anime).
// Vi bruker bare "data"-feltet som inneholder en liste av AnimeDto.
// ------------------------------------------------------
data class AnimeListResponse(
    val data: List<AnimeDto>
)

// ------------------------------------------------------
// AnimeDetailResponse
// Brukes når vi henter én anime via ID (GET /anime/{id}).
// "data" inneholder ett AnimeDto-objekt.
// ------------------------------------------------------
data class AnimeDetailResponse(
    val data: AnimeDto
)
