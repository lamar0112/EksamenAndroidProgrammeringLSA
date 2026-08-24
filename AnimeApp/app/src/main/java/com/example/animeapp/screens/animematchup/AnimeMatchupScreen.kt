package com.example.animeapp.screens.animematchup

// Importer for layout, scroll, kort og progress
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.animeapp.components.PrimaryButton
import com.example.animeapp.components.SecondaryButton

// ------------------------------------------------------
// AnimeMatchupScreen
// Skjerm 4: lite "spill" der brukeren velger
// den mest populære animeen av to.
// ------------------------------------------------------
@Composable
fun AnimeMatchupScreen(
    onBack: () -> Unit,
    viewModel: AnimeMatchupViewModel
) {
    val state = viewModel.uiState

    when {
        // Loading-visning
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Feilmelding + prøv igjen-knapp
        state.errorMessage != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.errorMessage,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(16.dp))
                PrimaryButton(
                    text = "Prøv igjen",
                    onClick = { viewModel.retry() }
                )
            }
        }

        // Vanlig visning
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 24.dp, bottom = 80.dp)
            ) {
                // Tittel + forklaring
                Text(
                    text = "Anime matchup",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Velg den mest populære animeen av de to. Vi sjekker om du har rett.",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(16.dp))

                // Tilbake-knapp
                SecondaryButton(
                    text = "Tilbake",
                    onClick = onBack,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Hvilken tror du er mest populær?",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(12.dp))

                val anime1 = state.anime1
                val anime2 = state.anime2

                // Valg-kort for de to animeene
                if (anime1 != null && anime2 != null) {
                    MatchupChoiceCard(
                        title = anime1.title ?: "Ukjent tittel",
                        backgroundColor = Color(0xFFE57373),
                        onClick = { viewModel.chooseAnime(anime1) }
                    )

                    Spacer(Modifier.height(12.dp))

                    MatchupChoiceCard(
                        title = anime2.title ?: "Ukjent tittel",
                        backgroundColor = Color(0xFF64B5F6),
                        onClick = { viewModel.chooseAnime(anime2) }
                    )
                } else {
                    Text(
                        text = "Ingen anime tilgjengelig akkurat nå.",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Siste valg + tilbakemelding
                if (state.lastChosenTitle != null) {
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
                                text = "Siste valg:",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                text = state.lastChosenTitle,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = "Score: ${state.lastChosenScore ?: "-"}  |  Popularitet (rang): ${state.lastChosenPopularity ?: "-"}",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Spacer(Modifier.height(8.dp))

                            when (state.lastWasMostPopular) {
                                true -> Text(
                                    text = "Du valgte den mest populære animeen av de to! 🎉",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                false -> Text(
                                    text = "Du traff ikke helt – den andre var mer populær.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                null -> Unit
                            }

                            if (!state.comparisonMessage.isNullOrBlank()) {
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = state.comparisonMessage!!,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Statistikk-kort (runder, riktige, streak osv.)
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
                            text = "Statistikk",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = "Runder spilt: ${state.totalRounds}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Riktige valg: ${state.correctRounds}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Streak nå: ${state.currentStreak}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Beste streak: ${state.bestStreak}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

// ------------------------------------------------------
// MatchupChoiceCard
// Lite kort som fungerer som "knapp" for hver anime.
// ------------------------------------------------------
@Composable
fun MatchupChoiceCard(
    title: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
