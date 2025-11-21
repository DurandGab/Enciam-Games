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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.enciamgames.ui.theme.haloFont
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PageFavoriJeuVideo(backStack: MutableList<Any>, viewModel: MainViewModel) {
    val favoris by viewModel.favoris.collectAsState()

    val dateFormatApi = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateFormatDisplay = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(favoris) { favori ->
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { backStack.add(Destination2(favori.id)) },
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                Box(modifier = Modifier.height(200.dp)) {
                    favori.jeu.background_image?.let { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = favori.jeu.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }?: Image(
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
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = favori.jeu.name,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = haloFont,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Note Metacritic : ${favori.jeu.metacritic ?: "N/A"} / 100",
                            color = Color(0xFFFFD700),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        val dateSortie = favori.jeu.released?.let {
                            try {
                                val parsedDate = dateFormatApi.parse(it)
                                parsedDate?.let { date -> dateFormatDisplay.format(date) } ?: "Non renseignée"
                            } catch (e: Exception) {
                                "Non renseignée"
                            }
                        } ?: "Non renseignée"

                        Text(
                            text = "Sortie : $dateSortie",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
        }
    }