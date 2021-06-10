package com.aleksejantonov.core.ui.model

interface ListItem {
  fun itemId(): Long

  companion object {
    const val PAGINATION_LOADING_ITEM_ID = -1L
    const val ADD_PRODUCT_ITEM_ID = -3L
    const val REMOVE_ALL_ITEM_ID = -5L
  }
}