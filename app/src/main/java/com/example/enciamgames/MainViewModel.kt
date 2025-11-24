package com.example.enciamgames

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.enciamgames.Models.DetailJeuVideo
import com.example.enciamgames.Models.JeuVideo
import com.example.enciamgames.Models.JeuVideoFavori
import com.example.enciamgames.Repository.MonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MonRepository(
        application
    )
    val jeuvideo = MutableStateFlow<List<JeuVideo>>(listOf())
    val detailjeuvideo = MutableStateFlow<DetailJeuVideo?>(null)
    val recherchenomjeuvideo = MutableStateFlow("")
    val favoris = MutableStateFlow<List<JeuVideoFavori>>(listOf())
    val recherchenomjeuvideofavori = MutableStateFlow("")
    val tri = mutableStateOf("none")
    val chargementPage = MutableStateFlow(false)
    private var pageActuelle = 1
    private var rechercheEnCours: String? = null

    init {
        chargerFavoris()
    }

    fun getJeuxVideos() {
        viewModelScope.launch {
            if (chargementPage.value) return@launch
            chargementPage.value = true

            val nomRecherche = rechercheEnCours ?: ""
            if (nomRecherche.isNotEmpty()) {
                getRechercheNomJeuVideo(nomRecherche)
            } else {
                val nouveauJeuVideo = repository.getJeuxVideosLesMieuxNotes(page = pageActuelle)
                val nouveaux = (nouveauJeuVideo as? List<*>)?.filterIsInstance<JeuVideo>() ?: listOf()
                jeuvideo.value = jeuvideo.value + nouveaux
                pageActuelle++
            }
            chargementPage.value = false
        }
    }
    fun getRechercheNomJeuVideo(nom: String) {
        viewModelScope.launch {
            if (chargementPage.value) return@launch
            chargementPage.value = true

            if (rechercheEnCours != nom) {
                rechercheEnCours = nom
                pageActuelle = 1
                jeuvideo.value = listOf()
            }

            val resultatRecherche = repository.rechercheJeuxVideosParNom(
                nom = nom,
                page = pageActuelle
            )

            val listeResultats = (resultatRecherche as? List<*>)?.filterIsInstance<JeuVideo>() ?: listOf()

            val resultatFiltre = listeResultats.filter {
                it.name.contains(nom, ignoreCase = true)
            }

            jeuvideo.value += resultatFiltre
            pageActuelle++

            chargementPage.value = false
        }
    }

    fun resetRecherche() {
        rechercheEnCours = null
        pageActuelle = 1
        jeuvideo.value = listOf()
        getJeuxVideos()
    }

    fun resetRechercheFavori() {
        recherchenomjeuvideofavori.value = ""
        chargerFavoris()
    }

    fun getDetailJeuVideo(id: Int) {
        viewModelScope.launch {
            detailjeuvideo.value = repository.getDetailJeuVideo(id)
        }
    }

    fun chargerFavoris() {
        viewModelScope.launch {
            favoris.value = repository.getJeuxVideosFavoris()

        }
    }

    fun ajouterFavori(id: Int) {
        jeuvideo.value.find { it.id == id }?.let {
            viewModelScope.launch {
                repository.ajouterJeuVideoFavori(it)
                chargerFavoris()
            }
        }
    }
    fun supprimerFavori(id: Int) {
        viewModelScope.launch {
            repository.supprimerJeuVideoFavori(id)
            chargerFavoris()
        }
    }

    fun rechercherFavoriParNom(nom: String) {
        viewModelScope.launch {
            val resultat = repository.rechercherJeuVideoFavoriNom(nom)
                recherchenomjeuvideofavori.value = nom
            favoris.value = resultat
        }
    }
}