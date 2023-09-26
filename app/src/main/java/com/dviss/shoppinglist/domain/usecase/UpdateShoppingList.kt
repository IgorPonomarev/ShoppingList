package com.dviss.shoppinglist.domain.usecase

import com.dviss.shoppinglist.domain.repository.ShoppingListRepository

class UpdateShoppingList(
    private val repository: ShoppingListRepository
) {

    suspend operator fun invoke(listId: Int) {
        repository.updateShoppingList(listId)
    }
}