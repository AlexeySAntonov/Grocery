package com.aleksejantonov.core.model

import org.threeten.bp.LocalDateTime

data class TrolleyModel(
  val id: Long,
  val name: String,
  val description: String,
  val created: LocalDateTime?,
  val products: List<ProductModel>,
  val syncStatus: SyncStatus,
)