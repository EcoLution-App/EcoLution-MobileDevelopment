package com.strawhead.ecolution.ui.screen.addhome

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationDataStore(private val context: Context) {

    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserAddress")
        val USER_ADDRESS_KEY = stringPreferencesKey("user_address")
    }

    // to get the email
    val getAddress: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ADDRESS_KEY] ?: ""
        }

    // to save the email
    suspend fun saveAddress(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ADDRESS_KEY] = name
        }
    }
}