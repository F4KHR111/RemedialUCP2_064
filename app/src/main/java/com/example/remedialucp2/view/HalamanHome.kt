package com.example.remedialucp2.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remedialucp2.data.room.entity.Buku
import com.example.remedialucp2.viewmodel.HomeUiState
import com.example.remedialucp2.viewmodel.HomeViewModel
import com.example.remedialucp2.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHome(
    onDetailClick: (Int) -> Unit,
    navigateToItemEntry: () -> Unit,
    onKategoriClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()

    Scaffold(
        topBar = {
            PerpustakaanTopAppBar(
                title = "Daftar Buku",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                // Tombol Ke Halaman Kategori (Penting buat Remedial)
                FloatingActionButton(
                    onClick = onKategoriClick,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(imageVector = Icons.Filled.List, contentDescription = "Kategori")
                }
                // Tombol Tambah Buku
                FloatingActionButton(
                    onClick = navigateToItemEntry,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Tambah Buku")
                }
            }
        },
    ) { innerPadding ->
        HomeBody(
            homeUiState = homeUiState,
            onBukuClick = onDetailClick,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeBody(
    homeUiState: HomeUiState,
    onBukuClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (homeUiState) {
        is HomeUiState.Loading -> Text("Loading...", modifier = modifier)
        is HomeUiState.Error -> Text("Terjadi Kesalahan", modifier = modifier)
        is HomeUiState.Success -> {
            if (homeUiState.buku.isEmpty()) {
                Text("Tidak ada data buku", modifier = modifier)
            } else {
                ListBuku(
                    listBuku = homeUiState.buku,
                    onItemClick = { onBukuClick(it.id) },
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ListBuku(
    listBuku: List<Buku>,
    onItemClick: (Buku) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = listBuku, key = { it.id }) { buku ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onItemClick(buku) },
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = buku.judul, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(text = "Penerbit: ${buku.penerbit}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}