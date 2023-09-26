package com.dviss.shoppinglist.domain.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun saveAuthKey(authKey: String)
    fun getAuthKey(): Flow<String>
}