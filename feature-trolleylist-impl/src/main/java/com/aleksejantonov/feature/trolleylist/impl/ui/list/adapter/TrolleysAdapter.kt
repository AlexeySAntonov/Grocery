package com.aleksejantonov.feature.trolleylist.impl.ui.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.aleksejantonov.core.ui.base.adapter.SimpleDiffAdapter
import com.aleksejantonov.core.ui.base.adapter.delegate.PaginationLoadingDelegate
import com.aleksejantonov.core.ui.base.adapter.delegate.RemoveAllItemDelegate
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.core.ui.model.TrolleyItem
import com.aleksejantonov.feature.trolleylist.impl.ui.list.adapter.delegate.TrolleyItemDelegate


class TrolleysAdapter(
  onTrolleyClick: (id: Long) -> Unit,
  onRemoveClick: (id: Long) -> Unit,
  onSyncClick: (id: Long) -> Unit,
  onRemoveAllClick: () -> Unit,
) : SimpleDiffAdapter(DIFF_CALLBACK) {
  init {
    delegatesManager
      .addDelegate(PaginationLoadingDelegate())
      .addDelegate(RemoveAllItemDelegate(onRemoveAllClick))
      .addDelegate(TrolleyItemDelegate(onTrolleyClick, onRemoveClick, onSyncClick))
  }

  companion object {
    const val PAYLOAD_PRODUCTS_COUNT = "PAYLOAD_PRODUCTS_COUNT"
    const val PAYLOAD_SYNC_STATUS = "PAYLOAD_SYNC_STATUS"

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
      override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.itemId() == newItem.itemId()
      }

      override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.equals(newItem)
      }

      override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Any? {
        if (oldItem is TrolleyItem && newItem is TrolleyItem) {
          if (oldItem.products.size != newItem.products.size) {
            return PAYLOAD_PRODUCTS_COUNT
          }
          if (oldItem.syncStatus != newItem.syncStatus) {
            return PAYLOAD_SYNC_STATUS
          }
        }
        return super.getChangePayload(oldItem, newItem)
      }
    }
  }
}