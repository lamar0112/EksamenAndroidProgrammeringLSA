package com.example.animeapp.data.api

// Importer for Retrofit-anrop
import retrofit2.http.GET
import retrofit2.http.Path

// ------------------------------------------------------
// AnimeApi
// Retrofit-interface som beskriver API-endepunktene vi bruker.
// Kalles fra AnimeRepository via AnimeApiClient.
// ------------------------------------------------------
interface AnimeApi {

    // -------------------------
    // Henter liste med anime
    // GET /anime
    // -------------------------
    @GET("anime")
    suspend fun getAnimeList(): AnimeListResponse

    // -------------------------
    // Henter én anime via ID
    // GET /anime/{id}
    // -------------------------
    @GET("anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: Int
    ): AnimeDetailResponse
}
