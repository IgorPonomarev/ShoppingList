package com.dviss.shoppinglist.ui

data class AuthState(
    val authKey: String = "",
    val isAuthenticated: Boolean = false,
    val showSnackBar: Boolean = false,
    val snackBarMessage: String = ""
)
