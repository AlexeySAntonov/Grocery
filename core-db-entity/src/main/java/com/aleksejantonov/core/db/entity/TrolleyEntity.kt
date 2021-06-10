package com.aleksejantonov.core.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aleksejantonov.core.model.SyncStatus
import org.threeten.bp.LocalDateTime

@Entity(tableName = "trolleys")
data class TrolleyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String = "",
    val description: String = "",
    val created: LocalDateTime? = null,
    val syncStatus: SyncStatus = SyncStatus.UPDATING,
)