package com.aleksejantonov.feature.trolleydetails.impl.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.aleksejantonov.core.ui.base.adapter.SimpleDiffAdapter
import com.aleksejantonov.core.ui.base.adapter.delegate.PaginationLoadingDelegate
import com.aleksejantonov.core.ui.base.adapter.delegate.RemoveAllItemDelegate
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.core.ui.model.ProductItem
import com.aleksejantonov.feature.trolleydetails.impl.ui.adapter.delegate.AddProductItemDelegate
import com.aleksejantonov.feature.trolleydetails.impl.ui.adapter.delegate.ProductItemDelegate


class ProductsAdapter(
  onCheckClick: (id: Long) -> Unit,
  onRemoveClick: (id: Long) -> Unit,
  onAddClick: (name: String) -> Unit,
  onSyncClick: (id: Long) -> Unit,
  onRemoveAllClick: () -> Unit,
) : SimpleDiffAdapter(DIFF_CALLBACK) {
  init {
    delegatesManager
      .addDelegate(PaginationLoadingDelegate())
      .addDelegate(RemoveAllItemDelegate(onRemoveAllClick))
      .addDelegate(ProductItemDelegate(onCheckClick, onRemoveClick, onSyncClick))
      .addDelegate(AddProductItemDelegate(onAddClick))
  }

  companion object {
    const val PAYLOAD_CHECKED = "PAYLOAD_CHECKED"
    const val PAYLOAD_SYNC_STATUS = "PAYLOAD_SYNC_STATUS"

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
      override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.itemId() == newItem.itemId()
      }

      override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.equals(newItem)
      }

      override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Any? {
        if (oldItem is ProductItem && newItem is ProductItem) {
          if (oldItem.isChecked != newItem.isChecked) {
            return PAYLOAD_CHECKED
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