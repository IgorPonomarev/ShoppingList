package com.dviss.shoppinglist.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val success: Boolean,
    val error: String? = null
)
