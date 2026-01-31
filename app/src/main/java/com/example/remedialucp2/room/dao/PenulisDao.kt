package com.example.remedialucp2.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.remedialucp2.room.entity.BukuPenulis
import com.example.remedialucp2.room.entity.Penulis
import kotlinx.coroutines.flow.Flow

@Dao
interface PenulisDao {

    // Ambil semua daftar penulis
    @Query("SELECT * FROM penulis ORDER BY nama ASC")
    fun getAllPenulis(): Flow<List<Penulis>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPenulis(penulis: Penulis)


    // 1. Simpan relasi (Hubungkan Buku ID sekian dengan Penulis ID sekian)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRelasiBukuPenulis(crossRef: BukuPenulis)

    // 2. Ambil semua penulis untuk Buku tertentu
    @Transaction
    @Query("""
        SELECT * FROM penulis 
        INNER JOIN buku_penulis ON penulis.id = buku_penulis.penulisId 
        WHERE buku_penulis.bukuId = :bukuId
    """)
    fun getPenulisByBukuId(bukuId: Int): Flow<List<Penulis>>
}