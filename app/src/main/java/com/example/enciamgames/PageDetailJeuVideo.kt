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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun fromHtml(html: String): AnnotatedString {
    val spanned: Spanned = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    return AnnotatedString(spanned.toString())
}
@Composable
fun PageDetailJeuVideo(backStack: MutableList<Any>, id: Int, viewModel: MainViewModel) {
val detailJeuVideo by viewModel.detailjeuvideo.collectAsState()

    LaunchedEffect(id) {
        viewModel.getDetailJeuVideo(id)
    }
    val jeu = detailJeuVideo?: return
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = jeu.name, fontSize = 24.sp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ThumbUp,
                    contentDescription = "Icone de note"
                )
                Text(text = "Note Métacritic: ${jeu.metacritic} / 100")
            }
            OutlinedButton(onClick = { /* TODO: Ajouter aux favoris */ }) {
                Text(text = "Ajouter aux favoris")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = fromHtml(jeu.description ?: "Pas de description disponible."))
        }
    }
}