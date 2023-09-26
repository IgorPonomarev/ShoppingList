package com.dviss.shoppinglist.data.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dviss.shoppinglist.domain.preferences.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val TAG = "PreferencesImpl"

class PreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    private object PreferencesKeys {
        val AUTH_KEY = stringPreferencesKey("auth_key")
    }

    override suspend fun saveAuthKey(authKey: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_KEY] = authKey
        }
    }

    override fun getAuthKey(): Flow<String> {
        return dataStore.data.catch {exception ->
            Log.d(TAG, "getAuthKey: ${exception.message}")
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {preferences ->
            preferences[PreferencesKeys.AUTH_KEY] ?: ""
        }
    }
}