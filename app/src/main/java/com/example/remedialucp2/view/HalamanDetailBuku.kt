package com.example.remedialucp2.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remedialucp2.data.room.entity.Buku
import com.example.remedialucp2.data.room.entity.BukuFisik
import com.example.remedialucp2.viewmodel.DetailBukuViewModel
import com.example.remedialucp2.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDetailBuku(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val buku by viewModel.detailBukuUiState.collectAsState()
    val listFisik by viewModel.listBukuFisik.collectAsState()

    Scaffold(
        topBar = {
            PerpustakaanTopAppBar(
                title = "Detail Buku",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding).padding(16.dp)) {
            // Bagian Header Info Buku
            buku?.let { itemBuku ->
                InfoBukuHeader(itemBuku)
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Daftar Fisik Buku (Stok)", style = MaterialTheme.typography.titleMedium)
            }

            // Bagian List Fisik Buku
            LazyColumn {
                items(listFisik) { fisik ->
                    CardFisik(fisik)
                }
            }
        }
    }
}

@Composable
fun InfoBukuHeader(buku: Buku) {
    Column {
        Text(text = buku.judul, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(text = "Penerbit: ${buku.penerbit}", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun CardFisik(fisik: BukuFisik) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (fisik.statusPeminjaman == "DIPINJAM")
                MaterialTheme.colorScheme.errorContainer
            else MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "ID: ${fisik.idFisik}", fontWeight = FontWeight.Bold)
                Text(text = "Kondisi: ${fisik.kondisi}")
            }
            Text(
                text = fisik.statusPeminjaman,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}