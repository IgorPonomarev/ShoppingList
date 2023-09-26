package com.dviss.shoppinglist.data.remote.dto

import com.dviss.shoppinglist.domain.model.ShoppingList
import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListDTO(
    val created: String,
    val name: String,
    val id: Int
)

fun ShoppingListDTO.toShoppingList(): ShoppingList {
    return ShoppingList(
        created = this.created,
        name = this.name,
        id = this.id
    )
}