package com.example.remedialucp2.repositori

import com.example.remedialucp2.room.dao.AuditDao
import com.example.remedialucp2.room.dao.BukuDao
import com.example.remedialucp2.room.dao.KategoriDao
import com.example.remedialucp2.room.dao.PenulisDao
import com.example.remedialucp2.room.entity.AuditLog
import com.example.remedialucp2.room.entity.Buku
import com.example.remedialucp2.room.entity.BukuFisik
import com.example.remedialucp2.room.entity.Kategori
import com.example.remedialucp2.room.entity.Penulis
import kotlinx.coroutines.flow.Flow

interface RepositoriPerpustakaan {
    fun getAllKategori(): Flow<List<Kategori>>
    fun getKategoriById(id: Int): Flow<Kategori>
    suspend fun insertKategori(kategori: Kategori)
    suspend fun updateKategori(kategori: Kategori)
    suspend fun deleteKategori(idKategori: Int)

    fun getAllBuku(): Flow<List<Buku>>
    fun getBukuById(id: Int): Flow<Buku>
    suspend fun insertBuku(buku: Buku)
    suspend fun updateBuku(buku: Buku)
    suspend fun deleteBuku(id: Int)

    fun getFisikByBukuId(bukuId: Int): Flow<List<BukuFisik>>
    suspend fun insertBukuFisik(bukuFisik: BukuFisik)

    fun getAllPenulis(): Flow<List<Penulis>>
    suspend fun insertPenulis(penulis: Penulis)
}

class OfflineRepositoriPerpustakaan(
    private val bukuDao: BukuDao,
    private val kategoriDao: KategoriDao,
    private val penulisDao: PenulisDao,
    private val auditDao: AuditDao
) : RepositoriPerpustakaan {

    override fun getAllKategori(): Flow<List<Kategori>> = kategoriDao.getAllKategori()

    override fun getKategoriById(id: Int): Flow<Kategori> = kategoriDao.getKategoriById(id)

    override suspend fun insertKategori(kategori: Kategori) {
        kategoriDao.insertKategori(kategori)
        auditDao.insertLog(AuditLog(tipeOperasi = "INSERT", tabelTerdampak = "Kategori", dataBaru = kategori.nama))
    }

    override suspend fun updateKategori(kategori: Kategori) {
        kategoriDao.updateKategori(kategori)
        auditDao.insertLog(AuditLog(tipeOperasi = "UPDATE", tabelTerdampak = "Kategori", dataBaru = kategori.nama))
    }

    override suspend fun deleteKategori(idKategori: Int) {
        val jumlahDipinjam = kategoriDao.countBukuDipinjamDiKategori(idKategori)
        if (jumlahDipinjam > 0) {
            throw Exception("Gagal: Kategori tidak dapat dihapus karena masih ada buku yang sedang DIPINJAM.")
        } else {
            kategoriDao.deleteKategori(idKategori)
            auditDao.insertLog(AuditLog(tipeOperasi = "SOFT_DELETE", tabelTerdampak = "Kategori", dataLama = "ID: $idKategori"))
        }
    }

    override fun getAllBuku(): Flow<List<Buku>> = bukuDao.getAllBuku()

    override fun getBukuById(id: Int): Flow<Buku> = bukuDao.getBukuById(id)

    override suspend fun insertBuku(buku: Buku) {
        bukuDao.insertBuku(buku)
        auditDao.insertLog(AuditLog(tipeOperasi = "INSERT", tabelTerdampak = "Buku", dataBaru = buku.judul))
    }

    override suspend fun updateBuku(buku: Buku) {
        bukuDao.updateBuku(buku)
        auditDao.insertLog(AuditLog(tipeOperasi = "UPDATE", tabelTerdampak = "Buku", dataBaru = buku.judul))
    }

    override suspend fun deleteBuku(id: Int) {
        bukuDao.deleteBuku(id)
        auditDao.insertLog(AuditLog(tipeOperasi = "SOFT_DELETE", tabelTerdampak = "Buku", dataLama = "ID: $id"))
    }

    override fun getFisikByBukuId(bukuId: Int): Flow<List<BukuFisik>> = bukuDao.getFisikByBukuId(bukuId)

    override suspend fun insertBukuFisik(bukuFisik: BukuFisik) {
        bukuDao.insertBukuFisik(bukuFisik)
    }

    override fun getAllPenulis(): Flow<List<Penulis>> = penulisDao.getAllPenulis()

    override suspend fun insertPenulis(penulis: Penulis) {
        penulisDao.insertPenulis(penulis)
    }
}