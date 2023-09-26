package com.dviss.shoppinglist.domain.usecase

import com.dviss.shoppinglist.domain.model.ShoppingListItem
import com.dviss.shoppinglist.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetShoppingListItems(
    private val repository: ShoppingListRepository
) {

    /**
     * returns flow of shopping list items
     */
    suspend operator fun invoke(listId: Int): Flow<List<ShoppingListItem>> {
        return if (listId != 0) {
            repository.getShoppingListItems(listId)
        } else {
            emptyFlow()
        }
    }
}