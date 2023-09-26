package com.dviss.shoppinglist.ui

import com.dviss.shoppinglist.domain.model.ShoppingList

data class ListsState(
    val shoppingLists: List<ShoppingList> = emptyList(),
    val currentListId: Int = 0
)
