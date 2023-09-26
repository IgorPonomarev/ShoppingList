package com.dviss.shoppinglist.domain.usecase

import com.dviss.shoppinglist.domain.preferences.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetAuthKey(
    private val preferences: PreferencesRepository
) {

    operator fun invoke(): Flow<String> {
        return preferences.getAuthKey()
    }
}