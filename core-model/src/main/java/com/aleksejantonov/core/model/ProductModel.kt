package com.aleksejantonov.core.model

import org.threeten.bp.LocalDateTime

data class ProductModel(
  val id: Long,
  val name: String,
  val trolleyId: Long,
  val created: LocalDateTime?,
  val isChecked: Boolean,
  val syncStatus: SyncStatus,
)