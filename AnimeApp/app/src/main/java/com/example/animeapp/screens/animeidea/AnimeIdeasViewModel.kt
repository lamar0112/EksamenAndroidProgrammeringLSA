package com.example.animeapp.screens.animeidea

// Importer for AndroidViewModel, state og coroutines
import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.db.AnimeIdea
import com.example.animeapp.data.db.AppDatabase
import kotlinx.coroutines.launch

// ------------------------------------------------------
// AnimeIdeasViewModel
// ViewModel for skjerm 3 (Room).
// Holder UiState og bruker DAO for å lagre/hente ideer.
// ------------------------------------------------------
class AnimeIdeasViewModel(application: Application) : AndroidViewModel(application) {

    // UiState som UI leser
    var uiState by mutableStateOf(AnimeIdeasUiState())
        private set

    // DAO for å snakke med Room
    private val dao = AppDatabase.getInstance(application).animeIdeaDao()

    // Last ideer når ViewModel starter
    init {
        loadIdeas()
    }

    // --------------------------------------------------
    // Henter alle ideer fra databasen
    // --------------------------------------------------
    private fun loadIdeas() {
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val ideas = dao.getAllIdeas()
                uiState = uiState.copy(
                    isLoading = false,
                    ideas = ideas
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Kunne ikke hente ideer: ${e.message}"
                )
            }
        }
    }

    // --------------------------------------------------
    // Oppdaterer input-feltene (tittel og beskrivelse)
    // --------------------------------------------------
    fun onTitleChange(newTitle: String) {
        uiState = uiState.copy(titleInput = newTitle)
    }

    fun onDescriptionChange(newDescription: String) {
        uiState = uiState.copy(descriptionInput = newDescription)
    }

    // --------------------------------------------------
    // Lagrer ny idé
    // --------------------------------------------------
    fun saveIdea() {
        val current = uiState
        val title = current.titleInput.trim()
        val description = current.descriptionInput.trim()

        // Validering
        if (title.isBlank() || description.isBlank()) {
            uiState = current.copy(
                errorMessage = "Tittel og beskrivelse kan ikke være tomme."
            )
            return
        }

        viewModelScope.launch {
            dao.insertIdea(
                AnimeIdea(
                    title = title,
                    description = description
                )
            )

            // Nullstill input
            uiState = uiState.copy(
                titleInput = "",
                descriptionInput = "",
                errorMessage = null
            )

            loadIdeas()
        }
    }

    // --------------------------------------------------
    // Oppdaterer en idé med gitt id
    // --------------------------------------------------
    fun updateIdea(id: Int, newTitle: String, newDescription: String) {
        viewModelScope.launch {
            dao.updateIdea(
                AnimeIdea(
                    id = id,
                    title = newTitle,
                    description = newDescription
                )
            )
            loadIdeas()
        }
    }

    // --------------------------------------------------
    // Sletter én idé
    // --------------------------------------------------
    fun deleteIdea(id: Int) {
        viewModelScope.launch {
            dao.deleteById(id)
            loadIdeas()
        }
    }

    // --------------------------------------------------
    // Sletter alle ideer
    // --------------------------------------------------
    fun deleteAll() {
        viewModelScope.launch {
            dao.deleteAll()
            loadIdeas()
        }
    }
}
