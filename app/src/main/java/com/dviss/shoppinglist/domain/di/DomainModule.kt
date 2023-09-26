package com.dviss.shoppinglist.domain.di

import com.dviss.shoppinglist.domain.preferences.PreferencesRepository
import com.dviss.shoppinglist.domain.repository.ShoppingListRepository
import com.dviss.shoppinglist.domain.usecase.AddToShoppingList
import com.dviss.shoppinglist.domain.usecase.AppUseCases
import com.dviss.shoppinglist.domain.usecase.CreateAccount
import com.dviss.shoppinglist.domain.usecase.CreateShoppingList
import com.dviss.shoppinglist.domain.usecase.CrossItOff
import com.dviss.shoppinglist.domain.usecase.GetAllShopLists
import com.dviss.shoppinglist.domain.usecase.GetAuthKey
import com.dviss.shoppinglist.domain.usecase.GetShoppingListItems
import com.dviss.shoppinglist.domain.usecase.LogIn
import com.dviss.shoppinglist.domain.usecase.RemoveFromList
import com.dviss.shoppinglist.domain.usecase.RemoveShoppingList
import com.dviss.shoppinglist.domain.usecase.UpdateShoppingList
import com.dviss.shoppinglist.domain.usecase.UpdateShoppingLists
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @ViewModelScoped
    @Provides
    fun provideUseCases(
        repository: ShoppingListRepository,
        preferences: PreferencesRepository
    ): AppUseCases {
        return AppUseCases(
            addToShoppingList = AddToShoppingList(repository),
            createAccount = CreateAccount(repository, preferences),
            createShoppingList = CreateShoppingList(repository),
            crossItOff = CrossItOff(repository),
            getAllShopLists = GetAllShopLists(repository),
            getShoppingListItems = GetShoppingListItems(repository),
            logIn = LogIn(repository, preferences),
            removeFromList = RemoveFromList(repository),
            removeShoppingList = RemoveShoppingList(repository),
            getAuthKey = GetAuthKey(preferences),
            updateShoppingList = UpdateShoppingList(repository),
            updateShoppingLists = UpdateShoppingLists(repository)
        )
    }
}