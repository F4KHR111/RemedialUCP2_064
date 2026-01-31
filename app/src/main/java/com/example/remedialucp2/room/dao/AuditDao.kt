package com.example.remedialucp2.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.remedialucp2.room.entity.AuditLog

@Dao
interface AuditDao {
    @Insert
    suspend fun insertLog(log: AuditLog)
}