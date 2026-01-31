package com.example.remedialucp2.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "buku",
    foreignKeys = [
        ForeignKey(
            entity = Kategori::class,
            parentColumns = ["id"],
            childColumns = ["kategoriId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["kategoriId"])]
)
data class Buku(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val judul: String,
    val penerbit: String,
    val kategoriId: Int,
    val isDeleted: Boolean = false
)

@Entity(
    tableName = "buku_fisik",
    foreignKeys = [
        ForeignKey(
            entity = Buku::class,
            parentColumns = ["id"],
            childColumns = ["bukuIndukId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["bukuIndukId"])]
)
data class BukuFisik(
    @PrimaryKey
    val idFisik: String,
    val bukuIndukId: Int,
    val kondisi: String,
    val statusPeminjaman: String = "TERSEDIA",
    val isDeleted: Boolean = false
)