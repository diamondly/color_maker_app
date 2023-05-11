package edu.fullerton.fz.persistencecolor

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MyPreferencesRepo private constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        private const val PREFERENCES_DATA_FILE_NAME = "settings"

        private val RED_KEY = stringPreferencesKey("redView")
        private val GREEN_KEY = stringPreferencesKey("blueView")
        private val BLUE_KEY = stringPreferencesKey("greenView")

        private var INSTANCE: MyPreferencesRepo? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
                }
                INSTANCE = MyPreferencesRepo(dataStore)
            }
        }
        fun get(): MyPreferencesRepo {
            return INSTANCE ?: throw IllegalStateException("AppPreferencesRepo not initialized yet")
        }
    }

    val redView: Flow<String> = this.dataStore.data.map { prefs ->
        prefs[RED_KEY] ?: "0.000"
    }.distinctUntilChanged()

    val greenView: Flow<String> = this.dataStore.data.map { prefs ->
        prefs[GREEN_KEY] ?: "0.000"
    }.distinctUntilChanged()

    val blueView: Flow<String> = this.dataStore.data.map { prefs ->
        prefs[BLUE_KEY] ?: "0.000"
    }.distinctUntilChanged()

    private suspend fun saveStringVal(key: Preferences.Key<String>, value: String) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    suspend fun saveInput(value: String, color: String) {
        val key: Preferences.Key<String> = when (color) {
            "red" -> RED_KEY
            "green" -> GREEN_KEY
            "blue" -> BLUE_KEY
            else -> {
                throw NoSuchFieldException("Invalid input color: $color")
            }
        }
        this.saveStringVal(key, value)
    }
}