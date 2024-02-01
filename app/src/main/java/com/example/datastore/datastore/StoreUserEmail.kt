package com.example.datastore.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreUserEmail(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserDetails")

        val USER_USERNAME_KEY = stringPreferencesKey("user_username")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    val getUsername: Flow<String?> get() = context.dataStore.data
        .map { preferences -> preferences[USER_USERNAME_KEY] ?: "" }

    val getEmail: Flow<String?> get() = context.dataStore.data
        .map { preferences -> preferences[USER_EMAIL_KEY] ?: "" }

    val getId: Flow<String?> get() = context.dataStore.data
        .map { preferences -> preferences[USER_ID_KEY] ?: "" }

    suspend fun saveDetails(username: String, email: String, id: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_USERNAME_KEY] = username
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_ID_KEY] = id
        }
    }

    suspend fun clearDetails() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_USERNAME_KEY)
            preferences.remove(USER_EMAIL_KEY)
            preferences.remove(USER_ID_KEY)
        }
    }
}
