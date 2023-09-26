package com.dviss.shoppinglist.domain.usecase

data class AppUseCases(
    val addToShoppingList: AddToShoppingList,
    val createAccount: CreateAccount,
    val createShoppingList: CreateShoppingList,
    val crossItOff: CrossItOff,
    val getAllShopLists: GetAllShopLists,
    val getShoppingListItems: GetShoppingListItems,
    val logIn: LogIn,
    val removeFromList: RemoveFromList,
    val removeShoppingList: RemoveShoppingList,
    val getAuthKey: GetAuthKey,
    val updateShoppingLists: UpdateShoppingLists,
    val updateShoppingList: UpdateShoppingList
)
