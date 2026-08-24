package com.example.animeapp.screens.allanime

// Importer for layout, bilde og styling
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.animeapp.data.api.AnimeDto

// ------------------------------------------------------
// AnimeListItem
// Viser én anime i lista på skjerm 1.
// Inneholder bilde, tittel, nøkkelinformasjon og kort beskrivelse.
// ------------------------------------------------------
@Composable
fun AnimeListItem(
    anime: AnimeDto
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {

            // Bilde på venstre side
            AsyncImage(
                model = anime.images?.jpg?.imageUrl,
                contentDescription = anime.title ?: "Anime image",
                modifier = Modifier
                    .width(100.dp)
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Tekstlig innhold på høyre side
            Column(
                modifier = Modifier.weight(1f)
            ) {

                // Tittel
                Text(
                    text = anime.title ?: "Uten tittel",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Info-rad: score | popularitet | episoder
                val scoreText = "Score: ${anime.score ?: "-"}"
                val popularityText = "Popularity: ${anime.popularity ?: "-"}"
                val episodesText = "Episodes: ${anime.episodes ?: "-"}"

                Text(
                    text = "$scoreText   |   $popularityText   |   $episodesText",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Divider()

                Spacer(modifier = Modifier.height(8.dp))

                // Underoverskrift for beskrivelse
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Selve beskrivelsen (kortet ned ved behov)
                val synopsis = anime.synopsis ?: "No description available."
                Text(
                    text = synopsis,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
