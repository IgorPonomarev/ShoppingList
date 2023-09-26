package com.dviss.shoppinglist.domain.usecase

import com.dviss.shoppinglist.domain.model.NetworkResponse
import com.dviss.shoppinglist.domain.repository.ShoppingListRepository

class AddToShoppingList(
    private val repository: ShoppingListRepository
) {

    /**
     * returns new item id if item successfully added and -1 if not
     */
    suspend operator fun invoke(listId: Int, name: String, amount: Int): Int {
        val result = repository.addItemToList(listId, name, amount)
        return if (result is NetworkResponse.ItemAdded) result.itemId else -1
    }
}