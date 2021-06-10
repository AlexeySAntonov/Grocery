package com.aleksejantonov.feature.trolleydetails.impl.ui.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.feature.trolleydetails.impl.R
import com.aleksejantonov.feature.trolleydetails.impl.databinding.ItemAddProductBinding
import com.aleksejantonov.feature.trolleydetails.impl.ui.adapter.delegate.items.AddProductItem
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class AddProductItemDelegate(
  private val onAddClick: (name: String) -> Unit,
) : AbsListItemAdapterDelegate<AddProductItem, ListItem, AddProductItemDelegate.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
    ViewHolder(ItemAddProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun isForViewType(item: ListItem, items: MutableList<ListItem>, position: Int): Boolean = item is AddProductItem

  override fun onBindViewHolder(item: AddProductItem, holder: ViewHolder, payloads: MutableList<Any>) {
    holder.bind()
  }

  inner class ViewHolder(private val binding: ItemAddProductBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
      with(binding) {
        productNameInput.doAfterTextChanged { editable ->
          editable?.let { enableAddButton(it.isNotBlank()) }
        }
        productNameInput.setOnEditorActionListener { _, actionId, _ ->
          if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (!productNameInput.text.isNullOrBlank()) dispatchAdd()
            return@setOnEditorActionListener true
          }
          return@setOnEditorActionListener false
        }
        enableAddButton(!productNameInput.text.isNullOrBlank())
        addButton.setOnClickListener {
          onAddClick.invoke(productNameInput.text.toString())
          productNameInput.setText("")
        }
      }
    }

    private fun enableAddButton(enabled: Boolean) {
      with(binding) {
        addButton.isEnabled = enabled
        addButton.setIconTintResource(if (enabled) R.color.appGreen else R.color.appGrey)
      }
    }

    private fun dispatchAdd() {
      with(binding) {
        onAddClick.invoke(productNameInput.text.toString())
        productNameInput.setText("")
      }
    }
  }
}