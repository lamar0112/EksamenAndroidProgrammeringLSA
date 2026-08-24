package com.example.animeapp.data.db

// Importer for Room-entity
import androidx.room.Entity
import androidx.room.PrimaryKey

// ------------------------------------------------------
// AnimeIdea
// Entity for Room-tabellen "anime_ideas" (brukes på skjerm 3).
// Representerer én idé som brukeren har laget.
// ------------------------------------------------------
@Entity(tableName = "anime_ideas")
data class AnimeIdea(
    // Primærnøkkel (auto-generert av Room)
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // Tittel på idéen
    val title: String,

    // Beskrivelse av idéen
    val description: String
)
