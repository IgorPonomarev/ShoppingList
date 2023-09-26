package com.dviss.shoppinglist.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllMyShopListsResponse(
    val success: Boolean,
    @SerialName("shop_list") val shoppingLists: List<ShoppingListDTO>
)
