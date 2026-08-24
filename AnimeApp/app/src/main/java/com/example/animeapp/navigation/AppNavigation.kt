package com.example.animeapp.navigation

// Importer vi trenger for navigasjon, layout og ikoner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Importer skjermene og viewmodelene
import com.example.animeapp.screens.allanime.AnimeListScreen
import com.example.animeapp.screens.allanime.AnimeListViewModel
import com.example.animeapp.screens.animeidea.AnimeIdeasViewModel
import com.example.animeapp.screens.animeideas.AnimeIdeasScreen
import com.example.animeapp.screens.animematchup.AnimeMatchupScreen
import com.example.animeapp.screens.animematchup.AnimeMatchupViewModel
import com.example.animeapp.screens.searchanimebyid.AnimeSearchScreen
import com.example.animeapp.screens.searchanimebyid.AnimeSearchViewModel

// ------------------------------------------------------
// AppNavigation
// Holder NavHost og BottomNavigationBar.
// Styrer hvilken skjerm vi er på og hvilke ViewModels som brukes.
// ------------------------------------------------------
@Composable
fun AppNavigation() {

    // Navigasjonskontroller
    val navController = rememberNavController()

    // Indeks for valgt ikon i bunnmenyen
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    // Ytre scaffold med bottom bar
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItemIndex = selectedItemIndex,
                onItemSelected = { index, route ->
                    selectedItemIndex = index

                    // Navigerer til valgt rute
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->

        // Selve innholdet over bottom bar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // -----------------------
            // ViewModels
            // -----------------------
            val listVm: AnimeListViewModel = viewModel()
            val searchVm: AnimeSearchViewModel = viewModel()
            val ideasVm: AnimeIdeasViewModel = viewModel()
            val matchupVm: AnimeMatchupViewModel = viewModel()

            // -----------------------
            // NavHost med alle skjermene
            // -----------------------
            NavHost(
                navController = navController,
                startDestination = Screen.AllAnime.route
            ) {

                // Skjerm 1: liste
                composable(Screen.AllAnime.route) {
                    selectedItemIndex = 0
                    AnimeListScreen(viewModel = listVm)
                }

                // Skjerm 2: søk
                composable(Screen.Search.route) {
                    selectedItemIndex = 1
                    AnimeSearchScreen(
                        viewModel = searchVm,
                        onBack = {
                            selectedItemIndex = 0
                            navController.navigate(Screen.AllAnime.route)
                        }
                    )
                }

                // Skjerm 3: ideer (Room)
                composable(Screen.Ideas.route) {
                    selectedItemIndex = 2
                    AnimeIdeasScreen(
                        viewModel = ideasVm,
                        onBack = {
                            selectedItemIndex = 0
                            navController.navigate(Screen.AllAnime.route)
                        }
                    )
                }

                // Skjerm 4: matchup
                composable(Screen.Matchup.route) {
                    selectedItemIndex = 3
                    AnimeMatchupScreen(
                        onBack = {
                            selectedItemIndex = 0
                            navController.navigate(Screen.AllAnime.route)
                        },
                        viewModel = matchupVm
                    )
                }
            }
        }
    }
}

// ------------------------------------------------------
// BottomNavigationBar
// Viser ikonene for de fire skjermene i bunnmenyen.
// ------------------------------------------------------
@Composable
fun BottomNavigationBar(
    selectedItemIndex: Int,
    onItemSelected: (Int, String) -> Unit
) {
    val items = listOf(
        Screen.AllAnime,
        Screen.Search,
        Screen.Ideas,
        Screen.Matchup
    )

    NavigationBar {
        items.forEachIndexed { index, screen ->

            val selected = index == selectedItemIndex

            // Velger ikon basert på valgt/ikke valgt
            val icon = when (screen) {
                Screen.AllAnime -> if (selected) Icons.Filled.List else Icons.Outlined.List
                Screen.Search -> if (selected) Icons.Filled.Search else Icons.Outlined.Search
                Screen.Ideas -> if (selected) Icons.Filled.Create else Icons.Outlined.Create
                Screen.Matchup -> if (selected) Icons.Filled.Favorite else Icons.Outlined.Favorite
            }

            NavigationBarItem(
                selected = selected,
                onClick = { onItemSelected(index, screen.route) },
                icon = { Icon(imageVector = icon, contentDescription = screen.label) },
                label = { Text(screen.label) }
            )
        }
    }
}
