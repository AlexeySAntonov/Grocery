package com.aleksejantonov.feature.trolleydetails.impl.ui.adapter.delegate.items

import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.core.ui.model.ListItem.Companion.ADD_PRODUCT_ITEM_ID

object AddProductItem : ListItem {

  override fun itemId(): Long = ADD_PRODUCT_ITEM_ID
}