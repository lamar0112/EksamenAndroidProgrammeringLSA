package com.example.animeapp.screens.animeideas

// Importer for layout, scroll og tekstfelt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// ViewModel + knapper
import com.example.animeapp.screens.animeidea.AnimeIdeasViewModel
import com.example.animeapp.components.PrimaryButton
import com.example.animeapp.components.SecondaryButton

// ------------------------------------------------------
// AnimeIdeasScreen
// Skjerm 3: bruker kan lage, redigere, filtrere og slette egne ideer.
// Data lagres i Room via AnimeIdeasViewModel.
// Har ekstra funksjoner (teller, filter, sortering).
// ------------------------------------------------------
@Composable
fun AnimeIdeasScreen(
    viewModel: AnimeIdeasViewModel,
    onBack: () -> Unit
) {
    val state = viewModel.uiState
    val ideas = state.ideas

    // Lokale felter for ny/redigert idé
    var editingId by rememberSaveable { mutableStateOf<Int?>(null) }
    var titleText by rememberSaveable { mutableStateOf("") }
    var descriptionText by rememberSaveable { mutableStateOf("") }

    // Filter og sortering
    var filterText by rememberSaveable { mutableStateOf("") }
    var sortAlphabetically by rememberSaveable { mutableStateOf(false) }

    // Filtrerte ideer basert på tittel
    val filteredIdeas = if (filterText.isBlank()) {
        ideas
    } else {
        ideas.filter { idea ->
            idea.title.contains(filterText, ignoreCase = true)
        }
    }

    // Liste som vises (filtrert + ev. sortert A–Å)
    val displayedIdeas = if (sortAlphabetically) {
        filteredIdeas.sortedBy { it.title.lowercase() }
    } else {
        filteredIdeas
    }

    // Ytre layout + scroll
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 24.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Tittel for skjermen
        Text(
            text = "Mine anime ideer",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        // Kort forklaring
        Text(
            text = "Skriv ned egne ideer, rediger dem og slett når du er ferdig.",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tilbake-knapp
        SecondaryButton(
            text = "Tilbake",
            onClick = onBack,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --------------------------------------------------
        // Kort for å lage eller redigere idé
        // --------------------------------------------------
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = if (editingId != null) "Rediger ide" else "Ny ide",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tittel-input
                OutlinedTextField(
                    value = titleText,
                    onValueChange = {
                        titleText = it
                        viewModel.onTitleChange(it)
                    },
                    label = { Text("Tittel på anime ide") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Beskrivelse-input
                OutlinedTextField(
                    value = descriptionText,
                    onValueChange = {
                        descriptionText = it
                        viewModel.onDescriptionChange(it)
                    },
                    label = { Text("Beskrivelse") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    singleLine = false
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Lagre / oppdater-knapp
                PrimaryButton(
                    text = if (editingId != null) "Oppdater ide" else "Lagre ide",
                    onClick = {
                        if (editingId != null) {
                            // Oppdater eksisterende idé
                            viewModel.updateIdea(
                                id = editingId!!,
                                newTitle = titleText,
                                newDescription = descriptionText
                            )
                        } else {
                            // Lagrer ny idé via ViewModel
                            viewModel.saveIdea()
                        }

                        // Nullstill lokal input etterpå
                        editingId = null
                        titleText = ""
                        descriptionText = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Viser feilmelding fra UiState hvis noe er feil
                state.errorMessage?.let { error ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Slett alle ideer-knapp
        PrimaryButton(
            text = "Slett alle ideer",
            onClick = { viewModel.deleteAll() },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Teller for antall ideer
        Text(
            text = "Du har ${ideas.size} ideer.",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Filterfelt for tittel
        OutlinedTextField(
            value = filterText,
            onValueChange = { filterText = it },
            label = { Text("Filtrer ideer på tittel") },
            singleLine = true,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Sorterings-knapp (A–Å / original rekkefølge)
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SecondaryButton(
                text = if (sortAlphabetically) "Vis original rekkefølge" else "Sorter A–Å",
                onClick = { sortAlphabetically = !sortAlphabetically },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --------------------------------------------------
        // Listevisning av ideer (med filter og sortering)
        // --------------------------------------------------
        when {
            state.isLoading -> {
                Text(
                    text = "Laster ideer...",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            displayedIdeas.isEmpty() -> {
                Text(
                    text = "Ingen ideer å vise. Skriv en ide og trykk \"Lagre ide\".",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            else -> {
                displayedIdeas.forEach { idea ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            // Tittel
                            Text(
                                text = idea.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // Beskrivelse
                            Text(
                                text = idea.description,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Knapper for slett/rediger
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                PrimaryButton(
                                    text = "Slett",
                                    onClick = { viewModel.deleteIdea(idea.id) }
                                )
                                PrimaryButton(
                                    text = "Rediger",
                                    onClick = {
                                        // Setter lokal state til valgt idé
                                        editingId = idea.id
                                        titleText = idea.title
                                        descriptionText = idea.description

                                        // Oppdaterer også UiState i ViewModel
                                        viewModel.onTitleChange(idea.title)
                                        viewModel.onDescriptionChange(idea.description)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
