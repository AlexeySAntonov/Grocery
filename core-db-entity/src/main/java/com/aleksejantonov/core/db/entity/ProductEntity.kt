package com.aleksejantonov.core.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.aleksejantonov.core.model.SyncStatus
import org.threeten.bp.LocalDateTime

@Entity(
  tableName = "products",
  foreignKeys = [
    ForeignKey(
      entity = TrolleyEntity::class,
      childColumns = ["trolleyId"],
      parentColumns = ["id"],
      onDelete = ForeignKey.NO_ACTION
    ),
  ],
  indices = [
    Index("trolleyId")
  ]
)
data class ProductEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0L,
  val trolleyId: Long = 0L,
  val name: String = "",
  val created: LocalDateTime? = null,
  val isChecked: Boolean = false,
  val syncStatus: SyncStatus = SyncStatus.UPDATING,
)