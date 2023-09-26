package com.dviss.shoppinglist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dviss.shoppinglist.domain.model.ShoppingListItem

@Entity(tableName = "shopping_lists_items")
data class ShoppingListItemEntity(
    val listId: Int,
    @PrimaryKey val itemId: Int,
    val amount: Int,
    val name: String,
    val isCrossed: Boolean
)

fun ShoppingListItemEntity.toShoppingListItem(): ShoppingListItem {
    return ShoppingListItem(
        amount = this.amount,
        name = this.name,
        itemId = this.itemId,
        isCrossed = this.isCrossed
    )
}

fun ShoppingListItem.toShoppingListItemEntity(listId: Int): ShoppingListItemEntity {
    return ShoppingListItemEntity(
        amount = this.amount,
        listId = listId,
        itemId = this.itemId,
        name = this.name,
        isCrossed = this.isCrossed
    )
}
