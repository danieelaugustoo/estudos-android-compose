package com.example.voos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

@Singleton
class UserDataRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val SEARCH_TEXT_KEY = stringPreferencesKey("search_text")

    val searchText: Flow<String> = context.userDataStore.data
        .map { preferences ->
            preferences[SEARCH_TEXT_KEY] ?: ""
        }

    suspend fun saveSearchText(searchText: String) {
        context.userDataStore.edit { preferences ->
            preferences[SEARCH_TEXT_KEY] = searchText
        }
    }
}