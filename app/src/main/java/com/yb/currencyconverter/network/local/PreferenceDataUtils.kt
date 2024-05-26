package com.yb.currencyconverter.network.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author BHATTJI
 * Created 25-05-2024 at 06:34 pm
 */
private val Context.dataStore by preferencesDataStore("DEFAULT")

val DEFAULT = stringPreferencesKey("route")


class PreferenceDataUtils @Inject constructor(@ApplicationContext context: Context) {


    private val dataStore = context.dataStore

    /**
     * Secure-ID
     */
    suspend fun setDefault(default: String) {
        dataStore.edit { user ->
            user[DEFAULT] = default
        }
    }

    fun getDefault() = dataStore.data.map { default ->
        default[DEFAULT] ?: ""
    }
}