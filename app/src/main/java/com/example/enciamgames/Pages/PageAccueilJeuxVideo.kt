package com.example.enciamgames.Pages

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.enciamgames.Activity.Destination2
import com.example.enciamgames.MainViewModel
import com.example.enciamgames.R
import com.example.enciamgames.ui.theme.haloFont
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PageAccueilJeuxVideo(backStack: MutableList<Any>, viewModel: MainViewModel) {
    val jeux by viewModel.jeuvideo.collectAsState()
    val chargementpage by viewModel.chargementPage.collectAsState()
    val favori by viewModel.favoris.collectAsState()
    val tri by viewModel.tri

    val dateFormatApi = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateFormatDisplay = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    LaunchedEffect(Unit) {
        viewModel.recherchenomjeuvideo.value = ""
        viewModel.getJeuxVideos()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        stickyHeader {
            BarreDeRecherche(viewModel = viewModel)
        }
        val jeuxTries = when (tri) {
            "playtime" -> jeux.sortedByDescending { it.playtime ?: 0 }
            "metacritic" -> jeux.sortedByDescending { it.metacritic ?: 0 }
            "released" -> jeux.sortedByDescending { jeu ->
                jeu.released?.let { dateStr ->
                    try {
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr)?.time
                    } catch (e: Exception) {
                        null
                    }
                } ?: 0L
            }

            else -> jeux
        }

        items(jeuxTries) { jeu ->
            val estFavori = favori.any { it.id == jeu.id }
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { backStack.add(Destination2(jeu.id)) },
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                Box(modifier = Modifier.height(200.dp)) {
                    jeu.background_image?.let { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = jeu.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } ?: Image(
                        painter = painterResource(id = R.drawable.jeuxvideopardefaut),
                        contentDescription = "Image de placeholder",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x80000000))
                    )
                    if (estFavori) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favori",
                            tint = Color.Red,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .clickable {
                                    viewModel.supprimerFavori(jeu.id)
                                }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favori",
                            tint = Color.Red,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .clickable {
                                    viewModel.ajouterFavori(jeu.id)
                                }
                        )
                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = jeu.name,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = haloFont,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Note Metacritic : ${jeu.metacritic ?: "N/A"} / 100",
                            color = Color(0xFFFFD700),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        val dateSortie = jeu.released?.let {
                            try {
                                val parsedDate = dateFormatApi.parse(it)
                                parsedDate?.let { date -> dateFormatDisplay.format(date) }
                                    ?: "Non renseignée"
                            } catch (e: Exception) {
                                "Non renseignée"
                            }
                        } ?: "Non renseignée"

                        Text(
                            text = "Sortie : $dateSortie",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Heures jouées par les utilisateurs : ${jeu.playtime ?: "N/A"} h",
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 10.sp,
                        )
                    }
                }
            }
        }
        item {
            if (chargementpage) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Chargement...", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
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

@Composable
fun BarreDeRecherche(viewModel: MainViewModel) {
    val recherchenomjeuvideo by viewModel.recherchenomjeuvideo.collectAsState()
    val tri by viewModel.tri
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = recherchenomjeuvideo,
                onValueChange = { newValue ->
                    viewModel.recherchenomjeuvideo.value = newValue
                    if (newValue.isEmpty()) viewModel.resetRecherche()
                },
                label = { Text("Rechercher un jeu vidéo") },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.getRechercheNomJeuVideo(recherchenomjeuvideo)
                        }
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Rechercher")
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Trier par",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.tri.value = "playtime" }
                        .padding(vertical = 6.dp)
                ) {
                    RadioButton(
                        selected = tri == "playtime",
                        onClick = { viewModel.tri.value = "playtime" }
                    )
                    Text("Les plus joués", modifier = Modifier.padding(start = 8.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.tri.value = "metacritic" }
                        .padding(vertical = 6.dp)
                ) {
                    RadioButton(
                        selected = tri == "metacritic",
                        onClick = { viewModel.tri.value = "metacritic" }
                    )
                    Text("Les mieux notés", modifier = Modifier.padding(start = 8.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.tri.value = "released" }
                        .padding(vertical = 6.dp)
                ) {
                    RadioButton(
                        selected = tri == "released",
                        onClick = { viewModel.tri.value = "released" }
                    )
                    Text("Dernières sorties", modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}

