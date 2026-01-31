package com.example.remedialucp2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remedialucp2.repositori.RepositoriPerpustakaan
import com.example.remedialucp2.room.entity.Buku
import com.example.remedialucp2.room.entity.Kategori
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EntryBukuViewModel(private val repositori: RepositoriPerpustakaan) : ViewModel() {

    // State Form saat ini
    var uiStateBuku by mutableStateOf(UiStateBuku())
        private set

    // Data untuk Dropdown Kategori (Diambil live dari DB)
    val kategoriList: StateFlow<List<Kategori>> = repositori.getAllKategori()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Update state saat user ngetik
    fun updateUiState(detailBuku: DetailBuku) {
        uiStateBuku = UiStateBuku(detailBuku = detailBuku, isEntryValid = validasiInput(detailBuku))
    }

    private fun validasiInput(uiState: DetailBuku): Boolean {
        return uiState.judul.isNotBlank() && uiState.penerbit.isNotBlank()
    }

    // Simpan ke Database
    suspend fun saveBuku() {
        if (validasiInput(uiStateBuku.detailBuku)) {
            repositori.insertBuku(uiStateBuku.detailBuku.toEntity())
        }
    }
}

// Helper Class untuk Form Data
data class UiStateBuku(
    val detailBuku: DetailBuku = DetailBuku(),
    val isEntryValid: Boolean = false
)

data class DetailBuku(
    val id: Int = 0,
    val judul: String = "",
    val penerbit: String = "",
    val kategoriId: Int = 0 // Menyimpan ID Kategori dari Dropdown
)

// Konversi Form ke Entity Database
fun DetailBuku.toEntity(): Buku = Buku(
    id = id,
    judul = judul,
    penerbit = penerbit,
    kategoriId = kategoriId
)