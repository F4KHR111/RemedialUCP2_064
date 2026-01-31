package com.example.remedialucp2.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.remedialucp2.RemedialApp
import com.example.remedialucp2.viewmodel.DetailBukuViewModel
import com.example.remedialucp2.viewmodel.EntryBukuViewModel
import com.example.remedialucp2.viewmodel.HomeViewModel
import com.example.remedialucp2.viewmodel.KategoriViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // 1. HomeViewModel
        initializer {
            HomeViewModel(aplikasiRemedial().container.repositoriPerpustakaan)
        }
        // 2. EntryBukuViewModel
        initializer {
            EntryBukuViewModel(aplikasiRemedial().container.repositoriPerpustakaan)
        }
        // 3. KategoriViewModel (Penting buat Remedial)
        initializer {
            KategoriViewModel(aplikasiRemedial().container.repositoriPerpustakaan)
        }
        // 4. DetailBukuViewModel
        initializer {
            DetailBukuViewModel(
                aplikasiRemedial().container.repositoriPerpustakaan,
                this.createSavedStateHandle() // Penting untuk ambil ID dari navigasi
            )
        }
    }
}

// Fungsi ekstensi untuk akses aplikasi
fun CreationExtras.aplikasiRemedial(): RemedialApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RemedialApp)