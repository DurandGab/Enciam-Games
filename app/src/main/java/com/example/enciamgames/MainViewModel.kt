package com.example.enciamgames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.listOf

class MainViewModel : ViewModel() {
    private val repository = MonRepository()

    val jeuvideo = MutableStateFlow<List<JeuVideo>>(listOf())
    val detailjeuvideo = MutableStateFlow<DetailJeuVideo?>(null)
    val recherchenomjeuvideo = MutableStateFlow("")
    val chargementPage = MutableStateFlow(false)

    private var pageActuelle = 1
    private var rechercheEnCours: String? = null

    fun getJeuxVideos() {
        viewModelScope.launch {
            if (chargementPage.value) return@launch
            chargementPage.value = true

            val nomRecherche = rechercheEnCours ?: ""

            if (nomRecherche.isNotEmpty()) {

                getRechercheNomJeuVideo(nomRecherche)
            } else {

                val nouveauJeuVideo = repository.getJeuxVideos(page = pageActuelle)
                jeuvideo.value += nouveauJeuVideo
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

            val resultatFiltre = resultatRecherche.filter {
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

    fun getDetailJeuVideo(id: Int) {
        viewModelScope.launch {
            detailjeuvideo.value = repository.getDetailJeuVideo(id)
        }
    }
}
