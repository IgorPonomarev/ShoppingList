package com.dviss.shoppinglist.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddToShoppingListResponse(
    val success: Boolean,
    @SerialName("item_id") val itemId: Int
)
