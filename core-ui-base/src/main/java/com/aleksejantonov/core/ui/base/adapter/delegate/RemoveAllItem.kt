package com.aleksejantonov.core.ui.base.adapter.delegate

import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.core.ui.model.ListItem.Companion.REMOVE_ALL_ITEM_ID

object RemoveAllItem : ListItem {
    override fun itemId(): Long = REMOVE_ALL_ITEM_ID
}