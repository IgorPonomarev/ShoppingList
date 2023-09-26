package com.dviss.shoppinglist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dviss.shoppinglist.domain.model.ShoppingList

@Entity(tableName = "shopping_lists")
data class ShoppingListEntity(
    @PrimaryKey val listId: Int? = null,
    val created: String,
    val name: String,
    val authKey: String
)

fun ShoppingListEntity.toShoppingList(): ShoppingList {
    return ShoppingList(
        created = this.created,
        name = this.name,
        id = this.listId ?: 0
    )
}

fun ShoppingList.toShoppingListEntity(authKey: String): ShoppingListEntity {
    return ShoppingListEntity(
        created = this.created,
        name = this.name,
        listId = this.id,
        authKey = authKey
    )
}
