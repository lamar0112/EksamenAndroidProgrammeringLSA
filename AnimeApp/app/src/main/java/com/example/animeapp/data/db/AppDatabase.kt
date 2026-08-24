package com.example.animeapp.data.db

// Importer for Room-database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// ------------------------------------------------------
// AppDatabase
// Room-databasen til appen.
// Holder styr på hvilke tabeller vi har og gir oss DAO-er.
// Bruker singleton-mønster slik at vi bare har én database.
// ------------------------------------------------------
@Database(
    entities = [AnimeIdea::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // DAO knyttet til databasen (Room lager selve implementasjonen)
    abstract fun animeIdeaDao(): AnimeIdeaDao

    // ------------------------------------------------------
    // Singleton-oppsett for databasen.
    // Vi bruker én instans slik at ting ikke krasjer eller
    // blir tregt ved at flere databaser prøver å kjøre samtidig.
    // ------------------------------------------------------
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Henter (eller lager) databasen én gang.
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "anime_db"   // Navnet på SQLite-filen som lagres på enheten
                ).build()

                INSTANCE = db
                db
            }
        }
    }
}
