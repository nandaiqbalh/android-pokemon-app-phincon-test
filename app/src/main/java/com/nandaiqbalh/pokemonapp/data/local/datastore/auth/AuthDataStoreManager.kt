package com.nandaiqbalh.pokemonapp.data.local.datastore.auth

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthDataStoreManager(@ApplicationContext private val context: Context) {

	suspend fun clear() {
		context.dataStore.edit {
			it.clear()
		}
	}

	val getUsername: Flow<String?> = context.dataStore.data.map {
		it[USERNAME_KEY]
	}

	suspend fun setUsername(username: String) {
		context.dataStore.edit {
			it[USERNAME_KEY] = username
		}
	}

	val getStatusAuth: Flow<Boolean?> = context.dataStore.data.map {
		it[STATUS_AUTH_KEY]
	}

	suspend fun setStatusAuth(statusAuth: Boolean) {
		context.dataStore.edit {
			it[STATUS_AUTH_KEY] = statusAuth
		}
	}

	companion object {
		private const val DATASTORE_NAME = "authdatastore_preferences"

		private val USERNAME_KEY = stringPreferencesKey("USERNAME_KEY")
		private val STATUS_AUTH_KEY = booleanPreferencesKey("STATUS_AUTH_KEY")

		private val Context.dataStore by preferencesDataStore(
			name = DATASTORE_NAME
		)
	}
}