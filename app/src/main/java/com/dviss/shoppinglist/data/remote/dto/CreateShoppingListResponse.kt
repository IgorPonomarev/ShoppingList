package com.dviss.shoppinglist.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateShoppingListResponse(
    val success: Boolean,
    @SerialName("list_id") val listId: Int
)
