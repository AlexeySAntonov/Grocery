package com.aleksejantonov.feature.trolleydetails.impl.ui.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.ui.base.mvvm.setBackgroundTint
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.core.ui.model.ProductItem
import com.aleksejantonov.feature.trolleydetails.impl.R
import com.aleksejantonov.feature.trolleydetails.impl.databinding.ItemProductBinding
import com.aleksejantonov.feature.trolleydetails.impl.ui.adapter.ProductsAdapter.Companion.PAYLOAD_CHECKED
import com.aleksejantonov.feature.trolleydetails.impl.ui.adapter.ProductsAdapter.Companion.PAYLOAD_SYNC_STATUS
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ProductItemDelegate(
  private val onCheckClick: (id: Long) -> Unit,
  private val onRemoveClick: (id: Long) -> Unit,
  private val onSyncClick: (id: Long) -> Unit,
) : AbsListItemAdapterDelegate<ProductItem, ListItem, ProductItemDelegate.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
    ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun isForViewType(item: ListItem, items: MutableList<ListItem>, position: Int): Boolean = item is ProductItem

  override fun onBindViewHolder(item: ProductItem, holder: ViewHolder, payloads: MutableList<Any>) {
    if (payloads.any { it == PAYLOAD_CHECKED }) {
      holder.updateChecked(item)
      return
    }
    if (payloads.any { it == PAYLOAD_SYNC_STATUS }) {
      holder.updateSyncStatus(item)
      return
    }
    holder.bind(item)
  }

  inner class ViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProductItem) {
      with(binding) {
        nameTextView.text = item.name
        updateChecked(item)
        updateSyncStatus(item)
        syncButton.setOnClickListener { onSyncClick.invoke(item.id) }
        removeButton.setOnClickListener { onRemoveClick.invoke(item.id) }
        card.setOnClickListener { onCheckClick.invoke(item.id) }
      }
    }

    fun updateChecked(item: ProductItem) {
      binding.checkbox.isChecked = item.isChecked
    }

    fun updateSyncStatus(item: ProductItem) {
      binding.syncButton.isEnabled = item.syncStatus != SyncStatus.DONE
      binding.syncButton.setBackgroundTint(
        when (item.syncStatus) {
          SyncStatus.DONE -> R.color.appGreen
          SyncStatus.UPDATING -> R.color.appBlue
          SyncStatus.CANCELED -> R.color.appYellow
          SyncStatus.FAILED -> R.color.appRed
        }
      )
    }
  }
}