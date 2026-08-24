package com.example.animeapp.screens.animematchup

// Importer for ViewModel, state og coroutines
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.api.AnimeDto
import com.example.animeapp.data.repository.AnimeRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

// ------------------------------------------------------
// AnimeMatchupViewModel
// ViewModel for skjerm 4 (matchup-spillet).
// Henter anime-liste, velger to og sjekker om brukeren
// velger den mest populære.
// ------------------------------------------------------
class AnimeMatchupViewModel : ViewModel() {

    // UiState som UI leser
    var uiState by mutableStateOf(AnimeMatchupUiState())
        private set

    // Full liste med anime fra API
    private var allAnime: List<AnimeDto> = emptyList()

    // Henter data og setter opp første par når ViewModel lages
    init {
        loadAnimeAndPick()
    }

    // --------------------------------------------------
    // Henter liste fra repository og velger første par
    // --------------------------------------------------
    private fun loadAnimeAndPick() {
        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {
            try {
                allAnime = AnimeRepository.getAnimeList()

                if (allAnime.size < 2) {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = "For få anime til matchup."
                    )
                } else {
                    pickNewPair()
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Kunne ikke hente anime: ${e.message}"
                )
            }
        }
    }

    // --------------------------------------------------
    // Velger to tilfeldige, forskjellige anime
    // --------------------------------------------------
    private fun pickNewPair() {
        if (allAnime.size < 2) return

        val shuffledIndices = allAnime.indices.shuffled(Random)
        val first = allAnime[shuffledIndices[0]]
        val second = allAnime[shuffledIndices[1]]

        uiState = uiState.copy(
            anime1 = first,
            anime2 = second,
            isLoading = false
        )
    }

    // --------------------------------------------------
    // Kalles når brukeren velger én av animeene
    // Oppdaterer tilbakemelding og statistikk
    // --------------------------------------------------
    fun chooseAnime(anime: AnimeDto) {
        val current = uiState

        // Finn den andre animeen i paret
        val other: AnimeDto? = when {
            current.anime1?.id == anime.id -> current.anime2
            current.anime2?.id == anime.id -> current.anime1
            else -> null
        }

        // Sammenligner score/popularitet og lager tekst
        val (comparisonText, choseMostPopularFlag) = other?.let { otherAnime ->
            val chosenScore = anime.score ?: 0.0
            val otherScore = otherAnime.score ?: 0.0

            val chosenPop = anime.popularity
            val otherPop = otherAnime.popularity

            // Del 1: score
            val scorePart = when {
                chosenScore > otherScore -> "Du valgte den med høyest score."
                chosenScore < otherScore -> "Den andre hadde litt høyere score."
                else -> "Begge har omtrent lik score."
            }

            // Del 2: popularitet (lavere tall = mer populær)
            val popularityPart: String
            val choseMostPopular: Boolean?

            if (chosenPop != null && otherPop != null) {
                when {
                    chosenPop < otherPop -> {
                        popularityPart = " Du valgte også den mest populære av de to."
                        choseMostPopular = true
                    }
                    chosenPop > otherPop -> {
                        popularityPart = " Den andre er faktisk mer populær."
                        choseMostPopular = false
                    }
                    else -> {
                        popularityPart = " De er omtrent like populære."
                        choseMostPopular = null
                    }
                }
            } else {
                popularityPart = ""
                choseMostPopular = null
            }

            scorePart + popularityPart to choseMostPopular
        } ?: (null to null)

        // Oppdater statistikk
        val newTotalRounds = current.totalRounds + 1
        val wasCorrect = (choseMostPopularFlag == true)

        val newCorrectRounds =
            if (wasCorrect) current.correctRounds + 1 else current.correctRounds

        val newCurrentStreak =
            if (wasCorrect) current.currentStreak + 1 else 0

        val newBestStreak = maxOf(current.bestStreak, newCurrentStreak)

        uiState = current.copy(
            lastChosenTitle = anime.title ?: "Ukjent",
            lastChosenPopularity = anime.popularity,
            lastChosenScore = anime.score,
            comparisonMessage = comparisonText,
            lastWasMostPopular = choseMostPopularFlag,
            totalRounds = newTotalRounds,
            correctRounds = newCorrectRounds,
            currentStreak = newCurrentStreak,
            bestStreak = newBestStreak
        )

        // Forbereder neste runde
        pickNewPair()
    }

    // --------------------------------------------------
    // Prøv igjen-knapp ved feil
    // --------------------------------------------------
    fun retry() {
        loadAnimeAndPick()
    }
}
