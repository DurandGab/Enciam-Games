package com.example.enciamgames

import android.text.Html
import android.text.Spanned
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

fun fromHtml(html: String): AnnotatedString {
    val spanned: Spanned = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    return AnnotatedString(spanned.toString())
}

@Composable
fun PageDetailJeuVideo(
    backStack: MutableList<Any>,
    id: Int,
    viewModel: MainViewModel
) {
    val detailJeuVideo by viewModel.detailjeuvideo.collectAsState()
    val favori by viewModel.favoris.collectAsState()
    val estFavori = favori.any { it.id == id }

    LaunchedEffect(id) {
        viewModel.getDetailJeuVideo(id)
    }

    val jeu = detailJeuVideo ?: return

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Text(
                text = jeu.name,
                fontSize = 26.sp
            )
        }

        item {
            jeu.background_image?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = jeu.name,
                    modifier = Modifier
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ThumbUp,
                    contentDescription = "Icône de note"
                )
                Text(text = "Note Metacritic : ${jeu.metacritic} / 100")
            }
        }
        if (estFavori) {
            item {
                OutlinedButton(onClick = { viewModel.supprimerFavori(jeu.id) }) {
                    Text(text = "Retirer des favoris")
                }
            }
        } else {
            item {
                OutlinedButton(onClick = { viewModel.ajouterFavori(jeu.id) }) {
                    Text(text = "Ajouter aux favoris")
                }
            }
        }

        item {
            Text(
                text = fromHtml(jeu.description ?: "Pas de description disponible."),
                fontSize = 14.sp
            )
        }
    }
}