package com.example.enciamgames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.listOf

class MainViewModel: ViewModel() {
    val repository = MonRepository()
    val jeuvideo = MutableStateFlow<List<JeuVideo>>(listOf())
    val detailjeuvideo = MutableStateFlow<DetailJeuVideo?>(null)

    fun getJeuxVideos() {
        viewModelScope.launch { jeuvideo.value = repository.getJeuxVideos() }
    }

    fun getDetailJeuVideo(id: Int) {
        viewModelScope.launch { detailjeuvideo.value = repository.getDetailJeuVideo(id) }
    }
}