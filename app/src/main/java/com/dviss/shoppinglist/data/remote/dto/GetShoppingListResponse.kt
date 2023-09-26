package com.dviss.shoppinglist.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetShoppingListResponse(
    val success: Boolean,
    @SerialName("item_list") val itemsLists: List<ShoppingListItemDTO>
)
