package com.example.animeapp.screens.animeidea

// Importer for layout, kort og knapper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.animeapp.data.db.AnimeIdea

// ------------------------------------------------------
// AnimeIdeaItem
// Viser én lagret idé i et kort (tittel, beskrivelse).
// Har knapper for å slette og redigere idéen.
// ------------------------------------------------------
@Composable
fun AnimeIdeaItem(
    idea: AnimeIdea,
    onDelete: (AnimeIdea) -> Unit,
    onEdit: (AnimeIdea) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            // Tittel på idéen
            Text(
                text = idea.title,
                style = MaterialTheme.typography.titleSmall,
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
            Row(modifier = Modifier.fillMaxWidth()) {

                Button(onClick = { onDelete(idea) }) {
                    Text("Slett")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { onEdit(idea) }) {
                    Text("Rediger")
                }
            }
        }
    }
}
