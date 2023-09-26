package com.dviss.shoppinglist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dviss.shoppinglist.data.local.entity.ShoppingListEntity
import com.dviss.shoppinglist.data.local.entity.ShoppingListItemEntity

@Database(
    entities = [
        ShoppingListItemEntity::class,
        ShoppingListEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ShoppingListDatabase: RoomDatabase() {

    abstract val dao:ShoppingListDao
}