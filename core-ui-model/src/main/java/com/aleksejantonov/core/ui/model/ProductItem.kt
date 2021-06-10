package com.aleksejantonov.core.ui.model

import com.aleksejantonov.core.model.ProductModel
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.model.TrolleyModel
import org.threeten.bp.LocalDateTime

data class ProductItem(
  val id: Long,
  val name: String,
  val created: LocalDateTime?,
  val isChecked: Boolean,
  val syncStatus: SyncStatus,
) : ListItem {

  override fun itemId(): Long = id

  companion object {

    fun from(model: ProductModel) = with(model) {
      ProductItem(
        id = id,
        name = name,
        created = created,
        isChecked = isChecked,
        syncStatus = syncStatus,
      )
    }

  }
}