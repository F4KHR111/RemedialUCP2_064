package com.example.remedialucp2.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_log")
data class AuditLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val waktu: Long = System.currentTimeMillis(),
    val tipeOperasi: String,
    val tabelTerdampak: String,
    val dataLama: String? = null,
    val dataBaru: String? = null,
    val userPelaku: String = "System"
)