package com.dviss.shoppinglist.data.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.dviss.shoppinglist.data.local.ShoppingListDatabase
import com.dviss.shoppinglist.data.preferences.PreferencesRepositoryImpl
import com.dviss.shoppinglist.data.remote.ShoppingListServiceImpl
import com.dviss.shoppinglist.data.repository.ShoppingListRepositoryImpl
import com.dviss.shoppinglist.domain.preferences.PreferencesRepository
import com.dviss.shoppinglist.domain.repository.ShoppingListRepository
import com.dviss.shoppinglist.domain.service.ShoppingListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideShoppingListDatabase(app: Application): ShoppingListDatabase {
        return Room.databaseBuilder(
            app,
            ShoppingListDatabase::class.java,
            "ShoppingList_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }

    @Provides
    @Singleton
    fun provideShoppingListService(client: HttpClient): ShoppingListService {
        return ShoppingListServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideShoppingListRepository(
        db: ShoppingListDatabase,
        shoppingListService: ShoppingListService
    ): ShoppingListRepository {
        return ShoppingListRepositoryImpl(db.dao, shoppingListService)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(dataStore: DataStore<Preferences>): PreferencesRepository {
        return PreferencesRepositoryImpl(dataStore)
    }
}