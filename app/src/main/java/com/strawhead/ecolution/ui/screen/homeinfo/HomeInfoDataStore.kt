package com.strawhead.ecolution.ui.screen.homeinfo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeInfoDataStore(private val context: Context) {

    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("HomeInfoDataStore")
        val title = stringPreferencesKey("title")
        val price = stringPreferencesKey("price")
        val address = stringPreferencesKey("address")
        val kecamatan = stringPreferencesKey("kecamatan")
        val description = stringPreferencesKey("description")
        val sellerName = stringPreferencesKey("sellerName")
        val sellerEmail = stringPreferencesKey("sellerEmail")
    }

    // to get the email
    val getTitle: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[title] ?: ""
        }

    val getPrice: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[price] ?: ""
        }

    val getAddress: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[address] ?: ""
        }

    val getKecamatan: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[kecamatan] ?: ""
        }

    val getDescription: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[description] ?: ""
        }

    val getSellerName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[sellerName] ?: ""
        }

    val getSellerEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[sellerEmail] ?: ""
        }

    suspend fun saveHomeInfo(keyName: String, info: String) {
        val homeInfoKey = stringPreferencesKey(keyName)
        context.dataStore.edit { preferences ->
            preferences[homeInfoKey] = info
        }
    }
}