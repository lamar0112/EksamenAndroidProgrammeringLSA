package com.example.animeapp.screens.allanime

// Importer for ViewModel, state og coroutines
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.repository.AnimeRepository
import kotlinx.coroutines.launch

// ------------------------------------------------------
// AnimeListViewModel
// ViewModel for skjerm 1 (anime-listen).
// Henter data fra API-et og holder på UiState.
// ------------------------------------------------------
class AnimeListViewModel : ViewModel() {

    // UiState som Compose-observatører leser
    var uiState by mutableStateOf(AnimeListUiState())
        private set

    // Last anime når ViewModel opprettes
    init {
        loadAnime()
    }

    // --------------------------------------------------
    // Henter liste med anime fra repository
    // --------------------------------------------------
    fun loadAnime() {
        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {
            try {
                val result = AnimeRepository.getAnimeList()
                uiState = uiState.copy(
                    isLoading = false,
                    animeList = result
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Kunne ikke hente anime: ${e.message}"
                )
            }
        }
    }

    // --------------------------------------------------
    // Oppdaterer filterteksten i UiState
    // (selve filtreringen gjøres i Composable)
    // --------------------------------------------------
    fun onFilterTextChange(newText: String) {
        uiState = uiState.copy(filterText = newText)
    }
}
