package com.example.remedialucp2.view.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.remedialucp2.view.route.DestinasiHome
import com.example.remedialucp2.view.route.DestinasiEntryBuku
import com.example.remedialucp2.view.route.DestinasiDetail
import com.example.remedialucp2.view.route.DestinasiKategori

@Composable
fun PetaNavigasi(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        // 1. Halaman Home (Dashboard)
        composable(
            route = DestinasiHome.route
        ) {
            HalamanHome(
                navigateToItemEntry = { navController.navigate(DestinasiEntryBuku.route) },
                onDetailClick = { itemId ->
                    navController.navigate("${DestinasiDetail.route}/$itemId")
                },
                onKategoriClick = { navController.navigate(DestinasiKategori.route) }
            )
        }

        // 2. Halaman Entry Buku (Tambah Buku)
        composable(route = DestinasiEntryBuku.route) {
            HalamanEntryBuku(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        // 3. Halaman Kategori (Manajemen Kategori - Sesuai Soal Remedial)
        composable(route = DestinasiKategori.route) {
            HalamanKategori(
                navigateBack = { navController.navigateUp() }
            )
        }

        // 4. Halaman Detail Buku
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.bukuId) {
                type = NavType.IntType
            })
        ) {
            HalamanDetailBuku(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}