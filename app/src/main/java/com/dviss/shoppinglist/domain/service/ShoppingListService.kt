package com.dviss.shoppinglist.domain.service

import com.dviss.shoppinglist.domain.model.NetworkResponse
import com.dviss.shoppinglist.domain.model.ShoppingList
import com.dviss.shoppinglist.domain.model.ShoppingListItem

interface ShoppingListService {

    //get auth key
    suspend fun createTestKey(): String

    //log in
    suspend fun authentication(authKey: String): NetworkResponse

    //should return list Id
    suspend fun createShoppingList(authKey: String, listName: String): Pair<Int, Boolean>

    //should return success status
    suspend fun removeShoppingList(listId: Int): Boolean

    suspend fun addToShoppingList(listId: Int, itemName: String, amount: Int): Pair<Int, Boolean>

    suspend fun crossItOff(listId: Int, itemId: Int): Boolean

    suspend fun removeFromList(listId: Int, itemId: Int): Boolean

    suspend fun getAllMyShopLists(authKey: String): List<ShoppingList>

    suspend fun getShoppingList(listId: Int): List<ShoppingListItem>
}