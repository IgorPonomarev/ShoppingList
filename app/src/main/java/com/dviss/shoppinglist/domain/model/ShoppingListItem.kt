package com.dviss.shoppinglist.domain.model

data class ShoppingListItem (
    val itemId: Int,
    val name: String,
    val isCrossed: Boolean,
    val amount: Int
)