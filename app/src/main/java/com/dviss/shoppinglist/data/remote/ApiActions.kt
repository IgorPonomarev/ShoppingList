package com.dviss.shoppinglist.data.remote

object ApiActions {
    object CreateTestKey {
        const val NAME = "CreateTestKey?"
    }

    object Authentication {
        const val NAME = "Authentication?"

        object Parameters {
            const val KEY = "key"
        }
    }

    object CreateShoppingList {
        const val NAME = "CreateShoppingList?"

        object Parameters {
            const val KEY = "key"
            const val NAME = "name"
        }
    }

    object RemoveShoppingList {
        const val NAME = "RemoveShoppingList?"

        object Parameters {
            const val LIST_ID = "list_id"
        }
    }

    object AddToShoppingList {
        const val NAME = "AddToShoppingList?"

        object Parameters {
            const val LIST_ID = "id"
            const val NAME = "value"
            const val AMOUNT = "n"
        }
    }

    object RemoveFromList {
        const val NAME = "RemoveFromList?"

        object Parameters {
            const val LIST_ID = "list_id"
            const val ITEM_ID = "item_id"
        }
    }

    object CrossItOff {
        const val NAME = "CrossItOff?"

        object Parameters {
            //API naming error
            const val LIST_ID = "item_id"
            const val ITEM_ID = "id"
        }
    }

    object GetAllMyShopLists {
        const val NAME = "GetAllMyShopLists?"

        object Parameters {
            const val KEY = "key"
        }
    }

    object GetShoppingList {
        const val NAME = "GetShoppingList?"

        object Parameters {
            const val LIST_ID = "list_id"
        }
    }
}