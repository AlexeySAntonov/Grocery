package com.aleksejantonov.core.ui.model

import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.model.TrolleyModel
import org.threeten.bp.LocalDateTime

data class TrolleyItem(
  val id: Long,
  val name: String,
  val description: String,
  val created: LocalDateTime?,
  val products: List<ProductItem>,
  val syncStatus: SyncStatus,
) : ListItem {

  override fun itemId(): Long = id

  companion object {

    fun from(model: TrolleyModel) = with(model) {
      TrolleyItem(
        id = id,
        name = name,
        description = description,
        created = created,
        products = products.map { ProductItem.from(it) },
        syncStatus = syncStatus,
      )
    }

  }
}