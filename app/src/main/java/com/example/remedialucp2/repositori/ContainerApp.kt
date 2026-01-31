package com.example.remedialucp2.repositori

import android.content.Context
import com.example.remedialucp2.room.PerpustakaanDatabase

interface AppContainer {
    val repositoriPerpustakaan: RepositoriPerpustakaan
}

class ContainerDataApp(private val context: Context) : AppContainer {

    private val database: PerpustakaanDatabase by lazy {
        PerpustakaanDatabase.getDatabase(context)
    }

    override val repositoriPerpustakaan: RepositoriPerpustakaan by lazy {
        OfflineRepositoriPerpustakaan(
            bukuDao = database.bukuDao(),
            kategoriDao = database.kategoriDao(),
            penulisDao = database.penulisDao(),
            auditDao = database.auditDao()
        )
    }
}