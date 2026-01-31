package com.example.remedialucp2.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remedialucp2.room.dao.AuditDao
import com.example.remedialucp2.room.dao.BukuDao
import com.example.remedialucp2.room.dao.KategoriDao
import com.example.remedialucp2.room.dao.PenulisDao
import com.example.remedialucp2.room.entity.AuditLog
import com.example.remedialucp2.room.entity.Buku
import com.example.remedialucp2.room.entity.BukuFisik
import com.example.remedialucp2.room.entity.BukuPenulis
import com.example.remedialucp2.room.entity.Kategori
import com.example.remedialucp2.room.entity.Penulis

@Database(
    entities = [
        Buku::class,
        BukuFisik::class,
        Kategori::class,
        Penulis::class,
        BukuPenulis::class,
        AuditLog::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PerpustakaanDatabase : RoomDatabase() {

    // Panggil DAO yang sudah dipisah tadi
    abstract fun bukuDao(): BukuDao
    abstract fun kategoriDao(): KategoriDao
    abstract fun auditDao(): AuditDao
    abstract fun penulisDao(): PenulisDao

    companion object {
        @Volatile
        private var Instance: PerpustakaanDatabase? = null

        fun getDatabase(context: Context): PerpustakaanDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    PerpustakaanDatabase::class.java,
                    "perpustakaan_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}