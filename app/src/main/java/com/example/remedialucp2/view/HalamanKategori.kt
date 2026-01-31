package com.example.remedialucp2.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remedialucp2.room.entity.Kategori
import com.example.remedialucp2.viewmodel.KategoriViewModel
import com.example.remedialucp2.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanKategori(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val listKategori by viewModel.kategoriUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val message = viewModel.snackbarMessage

    // Efek samping untuk menampilkan Snackbar jika ada pesan (Error/Sukses)
    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onSnackbarShown()
        }
    }

    Scaffold(
        topBar = {
            PerpustakaanTopAppBar(
                title = "Manajemen Kategori",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(listKategori) { kategori ->
                ItemKategori(
                    kategori = kategori,
                    onDelete = { viewModel.deleteKategori(kategori.id) }
                )
            }
        }
    }
}

@Composable
fun ItemKategori(
    kategori: Kategori,
    onDelete: () -> Unit
) {
    var deleteConfirm by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = kategori.nama, style = MaterialTheme.typography.titleMedium)
                Text(text = kategori.deskripsi, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { deleteConfirm = true }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus")
            }
        }
    }

    if (deleteConfirm) {
        AlertDialog(
            onDismissRequest = { deleteConfirm = false },
            title = { Text("Hapus Kategori") },
            text = { Text("Apakah Anda yakin ingin menghapus kategori ini? (Gagal jika ada buku dipinjam)") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    deleteConfirm = false
                }) { Text("Ya") }
            },
            dismissButton = {
                TextButton(onClick = { deleteConfirm = false }) { Text("Batal") }
            }
        )
    }
}