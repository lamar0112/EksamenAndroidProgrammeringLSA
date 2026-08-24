package com.example.animeapp.data.db

// Importer for Room DAO-funksjoner
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// ------------------------------------------------------
// AnimeIdeaDao
// DAO for tabellen "anime_ideas" (skjerm 3).
// Har spørringer for å hente, lagre, oppdatere og slette ideer.
// ------------------------------------------------------
@Dao
interface AnimeIdeaDao {

    // Henter alle ideene (sortert etter id)
    @Query("SELECT * FROM anime_ideas ORDER BY id ASC")
    suspend fun getAllIdeas(): List<AnimeIdea>

    // Lagrer en ny idé
    @Insert
    suspend fun insertIdea(idea: AnimeIdea)

    // Oppdaterer en idé
    @Update
    suspend fun updateIdea(idea: AnimeIdea)

    // Sletter én idé
    @Delete
    suspend fun deleteIdea(idea: AnimeIdea)

    // Sletter alle ideer
    @Query("DELETE FROM anime_ideas")
    suspend fun deleteAll()

    // Sletter en idé basert på id (brukes i ViewModel)
    @Query("DELETE FROM anime_ideas WHERE id = :id")
    suspend fun deleteById(id: Int)
}
