package com.dviss.shoppinglist.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrossItOffResponse(
    val success: Boolean,
    @SerialName("rows_affected") val rowsAffected: Int
)
