package com.example.animeapp.components

// Importer vi trenger for knappene
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Felles knappkomponenter brukt på tvers av appen.
// Gir lik stil på knapper og gjør koden ryddigere.

// -----------------------
// Primær knapp (hovedhandlinger)
// -----------------------
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(text = text)
    }
}

// -----------------------
// Sekundær knapp (tilbake / mindre viktige handlinger)
// -----------------------
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(text = text)
    }
}
