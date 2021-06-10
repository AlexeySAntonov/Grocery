package com.aleksejantonov.feature.trolleylist.impl.ui.list.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.ui.base.DateUtils
import com.aleksejantonov.core.ui.base.mvvm.setBackgroundTint
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.core.ui.model.TrolleyItem
import com.aleksejantonov.feature.trolleylist.impl.R
import com.aleksejantonov.feature.trolleylist.impl.databinding.ItemTrolleyBinding
import com.aleksejantonov.feature.trolleylist.impl.ui.list.adapter.TrolleysAdapter.Companion.PAYLOAD_PRODUCTS_COUNT
import com.aleksejantonov.feature.trolleylist.impl.ui.list.adapter.TrolleysAdapter.Companion.PAYLOAD_SYNC_STATUS
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class TrolleyItemDelegate(
  private val onTrolleyClick: (id: Long) -> Unit,
  private val onRemoveClick: (id: Long) -> Unit,
  private val onSyncClick: (id: Long) -> Unit,
) : AbsListItemAdapterDelegate<TrolleyItem, ListItem, TrolleyItemDelegate.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
    return ViewHolder(ItemTrolleyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun isForViewType(item: ListItem, items: MutableList<ListItem>, position: Int): Boolean = item is TrolleyItem
  override fun onBindViewHolder(item: TrolleyItem, holder: ViewHolder, payloads: MutableList<Any>) {
    if (payloads.any { it == PAYLOAD_PRODUCTS_COUNT }) {
      holder.updateProductsCount(item)
      return
    }
    if (payloads.any { it == PAYLOAD_SYNC_STATUS }) {
      holder.updateSyncStatus(item)
      return
    }
    holder.bind(item)
  }

  inner class ViewHolder(private val binding: ItemTrolleyBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TrolleyItem) {
      with(binding) {
        nameTextView.text = item.name
        descriptionTextView.text = item.description
        dateTextView.text = item.created?.let { DateUtils.longFormat(it, itemView.context) }
        updateProductsCount(item)
        updateSyncStatus(item)
        syncButton.setOnClickListener { onSyncClick.invoke(item.id) }
        removeButton.setOnClickListener { onRemoveClick.invoke(item.id) }
        card.setOnClickListener { onTrolleyClick.invoke(item.id) }
      }
    }

    fun updateProductsCount(item: TrolleyItem) {
      binding.countTextView.text = itemView.resources.getString(R.string.products_in_trolley, item.products.size)
    }

    fun updateSyncStatus(item: TrolleyItem) {
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