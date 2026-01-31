package com.example.remedialucp2.room.dao

import androidx.room.*

import com.example.remedialucp2.room.entity.Buku
import com.example.remedialucp2.room.entity.BukuFisik
import kotlinx.coroutines.flow.Flow

@Dao
interface BukuDao {
    @Query("SELECT * FROM buku WHERE isDeleted = 0")
    fun getAllBuku(): Flow<List<Buku>>

    @Query("SELECT * FROM buku WHERE id = :id")
    fun getBukuById(id: Int): Flow<Buku>

    // Ambil semua fisik dari 1 judul buku
    @Query("SELECT * FROM buku_fisik WHERE bukuIndukId = :bukuId AND isDeleted = 0")
    fun getFisikByBukuId(bukuId: Int): Flow<List<BukuFisik>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBuku(buku: Buku)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBukuFisik(bukuFisik: BukuFisik)

    @Update
    suspend fun updateBuku(buku: Buku)

    @Query("UPDATE buku SET isDeleted = 1 WHERE id = :id")
    suspend fun deleteBuku(id: Int)
}