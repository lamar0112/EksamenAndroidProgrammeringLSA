package com.example.animeapp.data.repository

// Importer vi trenger
import com.example.animeapp.data.api.AnimeApiClient
import com.example.animeapp.data.api.AnimeDto

// ------------------------------------------------------
// AnimeRepository
// Samler API-kall på ett sted slik at ViewModel ikke
// snakker direkte med Retrofit-klienten.
// Dette gir ryddigere lagdeling (som vi lærte i pensum).
// ------------------------------------------------------
object AnimeRepository {

    // Henter liste med anime fra API-et
    suspend fun getAnimeList(): List<AnimeDto> {
        val response = AnimeApiClient.api.getAnimeList()
        return response.data
    }

    // Henter én anime basert på ID
    suspend fun getAnimeById(id: Int): AnimeDto {
        val response = AnimeApiClient.api.getAnimeById(id)
        return response.data
    }
}
