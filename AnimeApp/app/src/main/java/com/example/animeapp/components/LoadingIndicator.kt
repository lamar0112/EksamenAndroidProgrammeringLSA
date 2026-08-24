package com.example.animeapp.components

// Importer for lastesymbol og layout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// -----------------------------------------
// LoadingIndicator
// Enkel komponent som viser at noe lastes.
// Brukes når UiState.isLoading = true.
// -----------------------------------------
@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
