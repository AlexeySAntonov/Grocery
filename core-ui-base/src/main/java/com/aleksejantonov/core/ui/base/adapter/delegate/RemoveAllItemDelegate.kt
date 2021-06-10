package com.aleksejantonov.core.ui.base.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.ui.base.databinding.ItemRemoveAllBinding
import com.aleksejantonov.core.ui.model.ListItem
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class RemoveAllItemDelegate(
  private val onRemoveAllClick: () -> Unit
) : AbsListItemAdapterDelegate<RemoveAllItem, ListItem, RemoveAllItemDelegate.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
    return ViewHolder(ItemRemoveAllBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun isForViewType(item: ListItem, items: MutableList<ListItem>, position: Int): Boolean = item is RemoveAllItem
  override fun onBindViewHolder(item: RemoveAllItem, holder: ViewHolder, payloads: MutableList<Any>) = holder.bind()

  inner class ViewHolder(private val binding: ItemRemoveAllBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
      binding.root.setOnClickListener { onRemoveAllClick.invoke() }
    }

  }
}