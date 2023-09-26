package com.dviss.shoppinglist.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RemoveFromListResponse(
    val success: Boolean
)
