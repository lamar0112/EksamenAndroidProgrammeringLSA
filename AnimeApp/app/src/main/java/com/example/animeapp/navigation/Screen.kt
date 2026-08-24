package com.example.animeapp.navigation

// ------------------------------------------------------
// Screen
// Holder alle routes som brukes i navigasjonen.
// route = selve ruten i NavHost
// label = teksten i bottom navigation bar
// ------------------------------------------------------
sealed class Screen(
    val route: String,
    val label: String
) {

    // Skjerm 1 – liste
    object AllAnime : Screen(
        route = "all_anime",
        label = "Alle anime"
    )

    // Skjerm 2 – søk
    object Search : Screen(
        route = "search",
        label = "Søk"
    )

    // Skjerm 3 – ideer (Room)
    object Ideas : Screen(
        route = "ideas",
        label = "Ideer"
    )

    // Skjerm 4 – matchup
    object Matchup : Screen(
        route = "matchup",
        label = "Matchup"
    )
}
