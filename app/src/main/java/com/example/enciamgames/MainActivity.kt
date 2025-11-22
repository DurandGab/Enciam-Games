package com.example.enciamgames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.enciamgames.Pages.PageAccueilJeuxVideo
import com.example.enciamgames.Pages.PageDetailJeuVideo
import com.example.enciamgames.Pages.PageFavoriJeuVideo
import com.example.enciamgames.ui.theme.EnciamGamesTheme


data object Destination1
data class Destination2(val id: Int)
data object Destination3

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel by viewModels()
            val backStack = remember { mutableStateListOf<Any>(Destination1) }
            val currentTitle = when (backStack.last()) {
                Destination1 -> "Enciams Games"
                is Destination2 -> "Detail du jeu"
                Destination3 -> "Favoris"
                else -> ""
            }
            EnciamGamesTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(currentTitle) },

                            navigationIcon = {
                                if (backStack.size > 1) {
                                    IconButton(onClick = { backStack.removeLastOrNull() }) {
                                        Icon(
                                            Icons.Default.ArrowBack,
                                            contentDescription = "Retour"
                                        )
                                    }
                                }
                            },

                            actions = {
                                IconButton(onClick = { backStack.add(Destination3) }) {
                                    Icon(
                                        Icons.Default.FavoriteBorder,
                                        contentDescription = "Favorite"
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavDisplay(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        backStack = backStack,
                        entryProvider = entryProvider {
                            entry<Destination1> { PageAccueilJeuxVideo(backStack, viewModel) }
                            entry<Destination2> { dest ->
                                PageDetailJeuVideo(dest.id, viewModel)
                            }
                            entry<Destination3> { PageFavoriJeuVideo(backStack, viewModel) }
                        }
                    )
                }
            }
        }
    }
}