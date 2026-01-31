package com.example.remedialucp2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remedialucp2.repositori.RepositoriPerpustakaan
import com.example.remedialucp2.room.entity.Buku
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// State UI untuk Home
sealed interface HomeUiState {
    data class Success(val buku: List<Buku>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel(private val repositori: RepositoriPerpustakaan) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = repositori.getAllBuku()
        .filterNotNull()
        .map { HomeUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading
        )
}