package com.dviss.shoppinglist.domain.model

sealed class NetworkResponse {
    object Success : NetworkResponse()
    data class Error(val message: String) : NetworkResponse()
    data class ListCreated(val listId: Int) : NetworkResponse()
    data class ItemAdded(val itemId: Int) : NetworkResponse()
    data class AccountCreated(val authKey: String): NetworkResponse()
}