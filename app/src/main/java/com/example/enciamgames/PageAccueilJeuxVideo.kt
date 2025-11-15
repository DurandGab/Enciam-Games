package com.example.enciamgames

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun PageAccueilJeuxVideo(backStack: MutableList<Any>, viewModel: MainViewModel) {
    val jeux by viewModel.jeuvideo.collectAsState()
    val chargementpage by viewModel.chargementPage.collectAsState()
    val recherchenomjeuvideo by viewModel.recherchenomjeuvideo.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getJeuxVideos()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        stickyHeader {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = recherchenomjeuvideo,
                    onValueChange = { newValue ->
                        viewModel.recherchenomjeuvideo.value = newValue
                        if (newValue.isEmpty()) {
                            viewModel.resetRecherche()
                        }
                    },
                    label = { Text("Rechercher un jeu") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        viewModel.getRechercheNomJeuVideo(
                            nom = recherchenomjeuvideo
                        )
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Rechercher")
                }
            }
        }

        items(jeux) { jeu ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        backStack.add(Destination2(jeu.id))
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Box(modifier = Modifier.height(180.dp)) {
                    jeu.background_image?.let { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = jeu.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x66000000))
                    )
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = jeu.name,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Note Metacritic: ${jeu.metacritic} / 100",
                            color = Color.Yellow,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Sorti le : ${jeu.released ?: "N/A"}",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
        item {
            if (chargementpage) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = "Chargement...")
                }
            }else{
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = {
                        viewModel.getJeuxVideos()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Charger plus de jeux")
                }
            }
        }
    }
}

