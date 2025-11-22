package com.example.enciamgames.Pages

import android.text.Html
import android.text.Spanned
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.enciamgames.MainViewModel
import com.example.enciamgames.R
import com.example.enciamgames.ui.theme.Purple40
import com.example.enciamgames.ui.theme.haloFont
import java.text.SimpleDateFormat
import java.util.Locale

fun fromHtml(html: String): AnnotatedString {
    val spanned: Spanned = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    return AnnotatedString(spanned.toString())
}

@Composable
fun PageDetailJeuVideo(
    id: Int,
    viewModel: MainViewModel
) {
    val detailJeuVideo by viewModel.detailjeuvideo.collectAsState()
    val favoris by viewModel.favoris.collectAsState()
    val estFavori = favoris.any { it.id == id }


    val dateFormatApi = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateFormatDisplay = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    LaunchedEffect(id) {
        viewModel.getDetailJeuVideo(id)
    }

    val jeu = detailJeuVideo ?: return

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                fontFamily = haloFont,
                text = jeu.name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        item {
            jeu.background_image?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = jeu.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(20.dp)),
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
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // --- Note Metacritic ---
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Icône de note",
                            tint = Color(0xFFFFD700)
                        )
                        Text(
                            text = "Metacritic : ${jeu.metacritic} / 100",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // --- Date de sortie ---
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Date de sortie du jeu",
                            tint = Color(0xFF4CAF50)
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
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Date de sortie du jeu",
                            tint = Color(0xFF2196F3)
                        )
                        Text(
                            text = "Heures moyennes jouées par les utilisateurs de RAWG API : ${jeu.playtime ?: "N/A"} h",
                            fontSize =12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }


        item {
            if (estFavori) {
                Button(
                    onClick = { viewModel.supprimerFavori(jeu.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Retirer des favoris")
                }
            } else {
                Button(
                    onClick = { viewModel.ajouterFavori(jeu.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = Purple40),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Ajouter aux favoris")
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Description",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                )
                Text(
                    text = fromHtml(jeu.description ?: "Pas de description disponible."),
                    fontSize = 15.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
