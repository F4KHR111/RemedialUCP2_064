package com.example.remedialucp2.view.route

import com.example.remedialucp2.R

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail_buku"
    override val titleRes = R.string.detail_buku

    // Argumen untuk membawa ID Buku
    const val bukuId = "bukuId"

    // Route lengkap dengan argumen: "detail_buku/{bukuId}"
    val routeWithArgs = "$route/{$bukuId}"
}