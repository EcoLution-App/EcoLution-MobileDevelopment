package com.strawhead.ecolution.ui.screen.home

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReloadDataStore(private val context: Context) {

    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Reload")
        val RELOAD_KEY = stringPreferencesKey("reload")
    }

    // to get the email
    val getReload: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[RELOAD_KEY] ?: "false"
        }

    // to save the email
    suspend fun saveReload(isReload: String) {
        context.dataStore.edit { preferences ->
            preferences[RELOAD_KEY] = isReload
        }
    }

}