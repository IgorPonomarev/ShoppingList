package com.dviss.shoppinglist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dviss.shoppinglist.data.local.entity.ShoppingListEntity
import com.dviss.shoppinglist.data.local.entity.ShoppingListItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateShoppingLists(lists: List<ShoppingListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateShoppingList(lists: List<ShoppingListItemEntity>)

    @Query("SELECT * FROM shopping_lists WHERE authKey = :authKey")
    fun getShoppingLists(authKey: String): Flow<List<ShoppingListEntity>>

    @Query("SELECT * FROM shopping_lists_items WHERE listId = :listId")
    fun getShoppingListItems(listId: Int): Flow<List<ShoppingListItemEntity>>

    @Query("DELETE FROM shopping_lists")
    suspend fun deleteShoppingLists()

    @Query("DELETE FROM shopping_lists_items WHERE listId = :listId")
    suspend fun deleteShoppingListItems(listId: Int)

    @Query("SELECT * FROM shopping_lists_items WHERE listId = :listId")
    suspend fun getShoppingListItemsOnce(listId: Int): List<ShoppingListItemEntity>

    @Query("DELETE FROM shopping_lists_items WHERE itemId IN (:itemIds)")
    suspend fun deleteItemsByIds(itemIds: List<Int>)

    @Query("SELECT * FROM shopping_lists WHERE authKey = :authKey")
    suspend fun getShoppingListsOnce(authKey: String): List<ShoppingListEntity>

    @Query("DELETE FROM shopping_lists WHERE listId IN (:listIds)")
    suspend fun deleteListsByIds(listIds: List<Int>)
}