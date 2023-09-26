package com.dviss.shoppinglist.domain.usecase

import com.dviss.shoppinglist.domain.model.NetworkResponse
import com.dviss.shoppinglist.domain.repository.ShoppingListRepository

class RemoveShoppingList(
    private val repository: ShoppingListRepository
) {

    /**
     * returns 0 if list successfully removed and -1 if not
     */
    suspend operator fun invoke(listId: Int): Int {
        val result = repository.removeShoppingList(listId)
        return if (result is NetworkResponse.Success) 0 else -1
    }
}