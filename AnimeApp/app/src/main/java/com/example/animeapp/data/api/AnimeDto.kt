package com.example.animeapp.data.api

// Importer for JSON-annotasjoner
import com.google.gson.annotations.SerializedName

// ------------------------------------------------------
// AnimeDto
// DTO for én anime slik den kommer fra Jikan API.
// Mapper JSON-felter til Kotlin-felter med @SerializedName.
// ------------------------------------------------------
data class AnimeDto(
    // "mal_id" → id
    @SerializedName("mal_id")
    val id: Int,

    // Tittel
    @SerializedName("title")
    val title: String? = null,

    // Kort beskrivelse
    @SerializedName("synopsis")
    val synopsis: String? = null,

    // Popularitetsrangering
    @SerializedName("popularity")
    val popularity: Int? = null,

    // Score-rating
    @SerializedName("score")
    val score: Double? = null,

    // Antall episoder
    @SerializedName("episodes")
    val episodes: Int? = null,

    // Bilder-objekt
    @SerializedName("images")
    val images: AnimeImagesDto? = null
)

// ------------------------------------------------------
// Bilder fra API-et (inneholder jpg m.m.)
// ------------------------------------------------------
data class AnimeImagesDto(
    @SerializedName("jpg")
    val jpg: AnimeJpgDto? = null
)

// ------------------------------------------------------
// Selve bilde-url-en i jpg-format
// ------------------------------------------------------
data class AnimeJpgDto(
    @SerializedName("image_url")
    val imageUrl: String? = null
)
