package com.dviss.shoppinglist.domain.usecase

import com.dviss.shoppinglist.domain.model.NetworkResponse
import com.dviss.shoppinglist.domain.preferences.PreferencesRepository
import com.dviss.shoppinglist.domain.repository.ShoppingListRepository

class CreateAccount(
    private val repository: ShoppingListRepository,
    private val preferences: PreferencesRepository
) {

    /**
     * returns new authKey if successfully created account and logged in and empty string if not
     */
    suspend operator fun invoke(): String {
        val result = repository.createAccount()
        if (result is NetworkResponse.AccountCreated) {
            preferences.saveAuthKey(result.authKey)
            val success = repository.logIn(result.authKey)
            return if (success is NetworkResponse.Success)
                result.authKey
            else
                ""
        }
        return ""
    }
}