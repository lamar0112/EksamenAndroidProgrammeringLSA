package com.example.animeapp.data.api

// Importer for Retrofit og OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ------------------------------------------------------
// AnimeApiClient
// Lager Retrofit-klienten vi bruker for å snakke med Jikan API.
// Følger oppsettet fra forelesningene (BASE_URL, logging, Gson).
// ------------------------------------------------------
object AnimeApiClient {

    // Base-url for API-et (må slutte med "/")
    private const val BASE_URL = "https://api.jikan.moe/v4/"

    // HTTP-logging (nyttig når vi tester)
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttp-klient med logging
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit-instansen (JSON-til-Kotlin via Gson)
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Gir oss implementasjonen av AnimeApi-interfacet
    val api: AnimeApi = retrofit.create(AnimeApi::class.java)
}
