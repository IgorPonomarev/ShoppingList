package com.dviss.shoppinglist.data.repository

import com.dviss.shoppinglist.data.local.ShoppingListDao
import com.dviss.shoppinglist.data.local.entity.toShoppingList
import com.dviss.shoppinglist.data.local.entity.toShoppingListEntity
import com.dviss.shoppinglist.data.local.entity.toShoppingListItem
import com.dviss.shoppinglist.data.local.entity.toShoppingListItemEntity
import com.dviss.shoppinglist.domain.model.NetworkResponse
import com.dviss.shoppinglist.domain.model.ShoppingList
import com.dviss.shoppinglist.domain.model.ShoppingListItem
import com.dviss.shoppinglist.domain.repository.ShoppingListRepository
import com.dviss.shoppinglist.domain.service.ShoppingListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ShoppingListRepositoryImpl(
    private val dao: ShoppingListDao,
    private val service: ShoppingListService
) : ShoppingListRepository {
    override suspend fun createAccount(): NetworkResponse {
        val key = service.createTestKey()
        return if (key.isNotEmpty())
            NetworkResponse.AccountCreated(key)
        else
            NetworkResponse.Error("")
    }

    override suspend fun logIn(authKey: String): NetworkResponse {
        return service.authentication(authKey)
    }

    override suspend fun getShoppingLists(authKey: String): Flow<List<ShoppingList>> {
        return dao.getShoppingLists(authKey).map { shoppingLists ->
            shoppingLists.map { it.toShoppingList() }
        }
    }

    override suspend fun getShoppingListItems(listId: Int): Flow<List<ShoppingListItem>> {
        return dao.getShoppingListItems(listId).map { shoppingListItems ->
            shoppingListItems.map { it.toShoppingListItem() }
        }
    }

    override suspend fun updateShoppingLists(authKey: String): NetworkResponse {
        val newLists: List<ShoppingList> = withContext(Dispatchers.IO) {
            service.getAllMyShopLists(authKey)
        }
        val existingLists: List<ShoppingList> = withContext(Dispatchers.IO) {
            dao.getShoppingListsOnce(authKey).map { it.toShoppingList() }
        }
        val listsToDelete = existingLists.filter { existingList ->
            newLists.none {newList ->
                newList.id == existingList.id
            }
        }
        withContext(Dispatchers.IO) {
            dao.deleteListsByIds(listsToDelete.map { it.id })
            dao.updateShoppingLists(newLists.map { it.toShoppingListEntity(authKey) })
        }
        return if (newLists.isNotEmpty())
            NetworkResponse.Success
        else
            NetworkResponse.Error("")
//        var lists: List<ShoppingList> = emptyList()
//        withContext(Dispatchers.IO) {
//            lists = service.getAllMyShopLists(authKey)
//        }
//        withContext(Dispatchers.IO) {
//            if (lists.isNotEmpty()) {
//                dao.deleteShoppingLists()
//                dao.updateShoppingLists(lists.map { it.toShoppingListEntity(authKey) })
//            }
//        }
//        return if (lists.isNotEmpty())
//            NetworkResponse.Success
//        else
//            NetworkResponse.Error("")
    }

    override suspend fun updateShoppingList(listId: Int): NetworkResponse {
        val newItems: List<ShoppingListItem> = withContext(Dispatchers.IO) {
            service.getShoppingList(listId)
        }
        val existingItems: List<ShoppingListItem> = withContext(Dispatchers.IO) {
            dao.getShoppingListItemsOnce(listId).map { it.toShoppingListItem() }
        }
        val itemsToDelete = existingItems.filter { existingItem ->
            // Check if the existing item is not present in the new list
            newItems.none { newItem ->
                newItem.itemId == existingItem.itemId
            }
        }
        withContext(Dispatchers.IO) {
            dao.deleteItemsByIds(itemsToDelete.map { it.itemId })
            dao.updateShoppingList(newItems.map { it.toShoppingListItemEntity(listId) })
        }
        return if (newItems.isNotEmpty())
            NetworkResponse.Success
        else
            NetworkResponse.Error("")
//        var items: List<ShoppingListItem> = emptyList()
//        withContext(Dispatchers.IO) {
//            items = service.getShoppingList(listId)
//        }
//        withContext(Dispatchers.IO) {
//            if (items.isNotEmpty()) {
//                dao.deleteShoppingListItems(listId)
//                dao.updateShoppingList(items.map { it.toShoppingListItemEntity(listId) })
//            }
//        }
//        return if (items.isNotEmpty())
//            NetworkResponse.Success
//        else
//            NetworkResponse.Error("")
    }

    override suspend fun addItemToList(listId: Int, name: String, amount: Int): NetworkResponse {
        val response = service.addToShoppingList(listId, name, amount)
        return if (response.second) {
            NetworkResponse.ItemAdded(response.first)
        } else {
            NetworkResponse.Error("")
        }
    }

    override suspend fun removeItemFromList(listId: Int, itemId: Int): NetworkResponse {
        val success = service.removeFromList(listId, itemId)
        return if (success) {
            NetworkResponse.Success
        } else {
            NetworkResponse.Error("")
        }
    }

    override suspend fun crossOffItem(listId: Int, itemId: Int): NetworkResponse {
        val success = service.crossItOff(listId, itemId)
        return if (success) {
            NetworkResponse.Success
        } else {
            NetworkResponse.Error("")
        }
    }

    override suspend fun createShoppingList(authKey: String, listName: String): NetworkResponse {
        val response = service.createShoppingList(authKey, listName)
        return if (response.second) {
            NetworkResponse.ListCreated(response.first)
        } else {
            NetworkResponse.Error("")
        }
    }

    override suspend fun removeShoppingList(listId: Int): NetworkResponse {
        val success = service.removeShoppingList(listId)
        return if (success) {
            NetworkResponse.Success
        } else {
            NetworkResponse.Error("")
        }
    }
}