package com.dviss.shoppinglist.domain.repository

import com.dviss.shoppinglist.domain.model.NetworkResponse
import com.dviss.shoppinglist.domain.model.ShoppingList
import com.dviss.shoppinglist.domain.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {

    suspend fun createAccount(): NetworkResponse

    suspend fun logIn(authKey: String): NetworkResponse

    suspend fun getShoppingLists(authKey: String): Flow<List<ShoppingList>>

    suspend fun getShoppingListItems(listId: Int): Flow<List<ShoppingListItem>>

    suspend fun updateShoppingLists(authKey: String): NetworkResponse

    suspend fun updateShoppingList(listId: Int): NetworkResponse

    suspend fun createShoppingList(authKey: String, listName: String): NetworkResponse

    suspend fun removeShoppingList(listId: Int): NetworkResponse

    suspend fun addItemToList(listId: Int, name: String, amount: Int): NetworkResponse

    suspend fun removeItemFromList(listId: Int, itemId: Int): NetworkResponse

    suspend fun crossOffItem(listId: Int, itemId: Int): NetworkResponse

}