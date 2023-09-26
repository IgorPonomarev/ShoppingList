package com.dviss.shoppinglist.domain.usecase

import com.dviss.shoppinglist.domain.model.NetworkResponse
import com.dviss.shoppinglist.domain.repository.ShoppingListRepository

class CrossItOff(
    private val repository: ShoppingListRepository
) {

    /**
     * returns 0 if item successfully crossed and -1 if not
     */
    suspend operator fun invoke(listId: Int, itemId: Int): Int {
        val result = repository.crossOffItem(listId, itemId)
        return if (result is NetworkResponse.Success) 0 else -1
    }
}