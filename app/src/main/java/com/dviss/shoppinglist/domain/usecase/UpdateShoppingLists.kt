package com.dviss.shoppinglist.domain.usecase

import com.dviss.shoppinglist.domain.repository.ShoppingListRepository

class UpdateShoppingLists(
    private val repository: ShoppingListRepository
) {

    suspend operator fun invoke(authKey: String) {
        if (authKey != "") {
            repository.updateShoppingLists(authKey)
        }
    }
}