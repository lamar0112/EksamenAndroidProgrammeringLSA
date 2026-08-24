package com.example.animeapp.screens.searchanimebyid

// Importer for ViewModel, state og coroutines
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.repository.AnimeRepository
import kotlinx.coroutines.launch

// ------------------------------------------------------
// AnimeSearchViewModel
// ViewModel for skjerm 2 (søk via anime-ID).
// Holder UiState og gjør API-kall gjennom repository.
// ------------------------------------------------------
class AnimeSearchViewModel : ViewModel() {

    // UiState som UI observerer
    var uiState by mutableStateOf(AnimeSearchUiState())
        private set

    // --------------------------------------------------
    // Søk etter anime med gitt ID
    // --------------------------------------------------
    fun search(idText: String) {
        val id = idText.toIntOrNull()

        // Validerer input
        if (id == null) {
            uiState = uiState.copy(
                result = null,
                error = "Du må skrive et gyldig tall.",
                isLoading = false
            )
            return
        }

        // Setter loading-state
        uiState = uiState.copy(
            isLoading = true,
            error = null,
            result = null
        )

        viewModelScope.launch {
            try {
                // Henter resultat
                val result = AnimeRepository.getAnimeById(id)

                // Oppdaterer historikk (maks 5 ID-er)
                val updatedHistory = (listOf(id) + uiState.lastSuccessfulIds)
                    .distinct()
                    .take(5)

                uiState = uiState.copy(
                    isLoading = false,
                    result = result,
                    lastSuccessfulIds = updatedHistory,
                    error = null
                )

            } catch (e: Exception) {
                // API-feil
                uiState = uiState.copy(
                    isLoading = false,
                    result = null,
                    error = "Noe gikk galt: ${e.message}"
                )
            }
        }
    }

    // --------------------------------------------------
    // Nullstiller resultat og feilmelding
    // --------------------------------------------------
    fun clear() {
        uiState = uiState.copy(
            isLoading = false,
            result = null,
            error = null
        )
    }
}
