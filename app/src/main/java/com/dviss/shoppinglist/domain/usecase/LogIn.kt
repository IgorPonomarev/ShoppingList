package com.dviss.shoppinglist.domain.usecase

import com.dviss.shoppinglist.domain.model.NetworkResponse
import com.dviss.shoppinglist.domain.preferences.PreferencesRepository
import com.dviss.shoppinglist.domain.repository.ShoppingListRepository

class LogIn(
    private val repository: ShoppingListRepository,
    private val preferences: PreferencesRepository
) {

    /**
     * returns 0 if successfully logged in and -1 if not
     */
    suspend operator fun invoke(authKey: String): NetworkResponse {
        val response = repository.logIn(authKey)
        if (response is NetworkResponse.Success)
            preferences.saveAuthKey(authKey)
        return response
    }
}