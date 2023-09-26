package com.dviss.shoppinglist.ui

import com.dviss.shoppinglist.domain.model.ShoppingListItem

data class ListState(
    val currentListItems: List<ShoppingListItem> = emptyList()
)
