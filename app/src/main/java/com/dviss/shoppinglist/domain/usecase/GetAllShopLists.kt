package com.dviss.shoppinglist.domain.usecase

import com.dviss.shoppinglist.domain.model.ShoppingList
import com.dviss.shoppinglist.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow

class GetAllShopLists(
    private val repository: ShoppingListRepository
) {

    /**
     * returns flow of shopping lists
     */
    suspend operator fun invoke(authKey: String): Flow<List<ShoppingList>> {
        return repository.getShoppingLists(authKey)
    }
}