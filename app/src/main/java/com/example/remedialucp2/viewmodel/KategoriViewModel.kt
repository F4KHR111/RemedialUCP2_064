package com.example.remedialucp2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remedialucp2.data.room.entity.Kategori
import com.example.remedialucp2.repositori.RepositoriPerpustakaan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class KategoriViewModel(private val repositori: RepositoriPerpustakaan) : ViewModel() {

    // Ambil semua kategori
    val kategoriUiState: StateFlow<List<Kategori>> = repositori.getAllKategori()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )

    // Pesan Snackbar (Sukses / Error Gagal Hapus)
    var snackbarMessage by mutableStateOf<String?>(null)
        private set

    fun deleteKategori(id: Int) {
        viewModelScope.launch {
            try {
                // Panggil fungsi delete di Repository (yang ada validasi throw Exception)
                repositori.deleteKategori(id)
                snackbarMessage = "Sukses: Kategori berhasil dihapus"
            } catch (e: Exception) {
                // Tangkap Error jika masih ada buku dipinjam
                snackbarMessage = e.message ?: "Gagal menghapus kategori"
            }
        }
    }

    fun onSnackbarShown() {
        snackbarMessage = null
    }
}