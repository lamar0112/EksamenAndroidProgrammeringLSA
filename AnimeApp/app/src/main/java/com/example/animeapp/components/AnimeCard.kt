package com.example.animeapp.components

// Importer vi trenger for kortet
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

// AnimeCard
// Gjenbrukbart kort som viser informasjon om én anime.
// Brukes flere steder i appen (liste, søk osv.).
@Composable
fun AnimeCard(
    title: String,
    imageUrl: String?,
    score: Double?,
    popularity: Int?,
    episodes: Int?,
    synopsis: String,
    modifier: Modifier = Modifier
) {
    // Selve kort-layouten
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Venstre side: bilde (eller tom plass hvis vi ikke har bilde)
            if (imageUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = title,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Spacer(modifier = Modifier.size(96.dp))
            }

            Spacer(modifier = Modifier.padding(8.dp))

            // Høyre side: tekst
            Column(modifier = Modifier.weight(1f)) {
                // Tittel
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Score, popularitet og episoder på én linje
                Text(
                    text = buildString {
                        append("Score: ${score ?: "-"}  |  ")
                        append("Popularitet: ${popularity ?: "-"}  |  ")
                        append("Episoder: ${episodes ?: "-"}")
                    },
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Divider()

                Spacer(modifier = Modifier.height(8.dp))

                // Liten overskrift før beskrivelse
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )

                // Kortet ned synopsis (viser maks 4 linjer)
                Text(
                    text = synopsis,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
