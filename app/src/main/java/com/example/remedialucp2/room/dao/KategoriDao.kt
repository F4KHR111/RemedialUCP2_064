package com.example.remedialucp2.room.dao

import androidx.room.*
import com.example.remedialucp2.room.entity.Kategori
import kotlinx.coroutines.flow.Flow

@Dao
interface KategoriDao {
    @Query("SELECT * FROM kategori WHERE isDeleted = 0 ORDER BY nama ASC")
    fun getAllKategori(): Flow<List<Kategori>>

    @Query("SELECT * FROM kategori WHERE id = :id")
    fun getKategoriById(id: Int): Flow<Kategori>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKategori(kategori: Kategori)

    @Update
    suspend fun updateKategori(kategori: Kategori)

    // Soft Delete
    @Query("UPDATE kategori SET isDeleted = 1 WHERE id = :id")
    suspend fun deleteKategori(id: Int)

    // Cek Buku Dipinjam (Penting untuk Soal Remedial!)
    @Query("""
        SELECT COUNT(*) FROM buku_fisik 
        INNER JOIN buku ON buku_fisik.bukuIndukId = buku.id 
        WHERE buku.kategoriId = :kategoriId 
        AND buku_fisik.statusPeminjaman = 'DIPINJAM' 
        AND buku_fisik.isDeleted = 0
    """)
    suspend fun countBukuDipinjamDiKategori(kategoriId: Int): Int
}