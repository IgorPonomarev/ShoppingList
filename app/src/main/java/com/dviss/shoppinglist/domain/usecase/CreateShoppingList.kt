package com.dviss.shoppinglist.domain.usecase

import com.dviss.shoppinglist.domain.model.NetworkResponse
import com.dviss.shoppinglist.domain.repository.ShoppingListRepository

class CreateShoppingList(
    private val repository: ShoppingListRepository
) {

    /**
     * returns new list id if list successfully created and -1 if not
     */
    suspend operator fun invoke(authKey: String, name: String): Int {
        val result = repository.createShoppingList(authKey, name)
        return if (result is NetworkResponse.ListCreated) result.listId else -1
    }
}