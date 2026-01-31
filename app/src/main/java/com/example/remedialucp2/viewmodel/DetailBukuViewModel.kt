package com.example.remedialucp2.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remedialucp2.repositori.RepositoriPerpustakaan
import com.example.remedialucp2.room.entity.Buku
import com.example.remedialucp2.room.entity.BukuFisik
import com.example.remedialucp2.view.route.DestinasiDetail
import kotlinx.coroutines.flow.*

class DetailBukuViewModel(
    private val repositori: RepositoriPerpustakaan,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Ambil ID Buku dari argumen Navigasi
    private val bukuId: Int = checkNotNull(savedStateHandle[DestinasiDetail.bukuId])

    // State Flow Data Buku (Judul, dll)
    val detailBukuUiState: StateFlow<Buku?> = repositori.getBukuById(bukuId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // State Flow Data Fisik Buku (Item Nyata)
    val listBukuFisik: StateFlow<List<BukuFisik>> = repositori.getFisikByBukuId(bukuId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}