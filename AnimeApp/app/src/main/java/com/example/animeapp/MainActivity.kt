package com.example.animeapp

// Importer for Activity, Compose og tema
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// Navigation + tema
import com.example.animeapp.navigation.AppNavigation
import com.example.animeapp.ui.theme.AnimeAppTheme

// ------------------------------------------------------
// MainActivity
// Startpunktet for appen. Setter opp Compose, tema og
// sender videre til AppNavigation som håndterer skjermene.
// ------------------------------------------------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Samme som forelesningene (CarApp m.fl.)
        enableEdgeToEdge()

        setContent {
            AnimeAppTheme {

                // Ytre layout
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        // Her kobler vi oss på hele navigasjonen i appen.
                        AppNavigation()
                    }
                }
            }
        }
    }
}

// ------------------------------------------------------
// Preview (standard fra Compose-malen)
// ------------------------------------------------------
@Composable
fun Greeting(name: String) {
    androidx.compose.material3.Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimeAppTheme {
        Greeting("AnimeApp")
    }
}
