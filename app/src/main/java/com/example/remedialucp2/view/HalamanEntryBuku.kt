package com.example.remedialucp2.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remedialucp2.data.room.entity.Kategori
import com.example.remedialucp2.viewmodel.DetailBuku
import com.example.remedialucp2.viewmodel.EntryBukuViewModel
import com.example.remedialucp2.viewmodel.UiStateBuku
import com.example.remedialucp2.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntryBuku(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Ambil data untuk Dropdown
    val listKategori by viewModel.kategoriList.collectAsState()

    Scaffold(
        topBar = {
            PerpustakaanTopAppBar(
                title = "Tambah Buku",
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        EntryBody(
            uiStateBuku = viewModel.uiStateBuku,
            listKategori = listKategori,
            onBukuValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveBuku()
                    navigateBack()
                }
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun EntryBody(
    uiStateBuku: UiStateBuku,
    listKategori: List<Kategori>,
    onBukuValueChange: (DetailBuku) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FormInput(
            detailBuku = uiStateBuku.detailBuku,
            listKategori = listKategori,
            onValueChange = onBukuValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStateBuku.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    detailBuku: DetailBuku,
    listKategori: List<Kategori>,
    onValueChange: (DetailBuku) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedKategoriNama by remember { mutableStateOf("") }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            value = detailBuku.judul,
            onValueChange = { onValueChange(detailBuku.copy(judul = it)) },
            label = { Text("Judul Buku") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = detailBuku.penerbit,
            onValueChange = { onValueChange(detailBuku.copy(penerbit = it)) },
            label = { Text("Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // --- DROPDOWN KATEGORI ---
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedKategoriNama,
                onValueChange = {},
                readOnly = true,
                label = { Text("Pilih Kategori") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listKategori.forEach { kategori ->
                    DropdownMenuItem(
                        text = { Text(kategori.nama) },
                        onClick = {
                            selectedKategoriNama = kategori.nama
                            onValueChange(detailBuku.copy(kategoriId = kategori.id))
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}