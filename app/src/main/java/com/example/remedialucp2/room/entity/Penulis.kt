package com.example.remedialucp2.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "penulis")
data class Penulis(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val bio: String
)

@Entity(
    tableName = "buku_penulis",
    primaryKeys = ["bukuId", "penulisId"],
    foreignKeys = [
        ForeignKey(entity = Buku::class, parentColumns = ["id"], childColumns = ["bukuId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Penulis::class, parentColumns = ["id"], childColumns = ["penulisId"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index(value = ["penulisId"])]
)
data class BukuPenulis(
    val bukuId: Int,
    val penulisId: Int
)