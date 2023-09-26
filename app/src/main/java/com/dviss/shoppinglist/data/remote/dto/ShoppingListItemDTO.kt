package com.dviss.shoppinglist.data.remote.dto

import com.dviss.shoppinglist.domain.model.ShoppingListItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListItemDTO(
    val created: Int,
    val name: String,
    @SerialName("is_crossed") val isCrossed: Boolean,
    val id: Int
)

fun ShoppingListItemDTO.toShoppingListItem(): ShoppingListItem {
    return ShoppingListItem(
        amount = this.created,
        name = this.name,
        itemId = this.id,
        isCrossed = this.isCrossed
    )
}