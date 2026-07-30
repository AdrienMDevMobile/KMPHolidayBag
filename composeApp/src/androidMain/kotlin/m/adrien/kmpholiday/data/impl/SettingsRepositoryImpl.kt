package m.adrien.kmpholiday.data.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import m.adrien.kmpholiday.domain.Settings

actual class SettingsRepositoryImpl : m.adrien.kmpholiday.domain.repository.SettingsRepository {
    companion object {
        private lateinit var androidContext: Context
        
        fun init(context: Context) {
            androidContext = context
        }
        
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        private val KEEP_SCREEN_ON_KEY = booleanPreferencesKey("keep_screen_on")
    }

    actual override fun get(): Flow<Settings> {
        return androidContext.dataStore.data.map { preferences ->
            val keepScreenOn = preferences[KEEP_SCREEN_ON_KEY] ?: false
            Settings(keepScreenOn = keepScreenOn)
        }
    }

    actual override suspend fun setKeepScreenOn(value: Boolean): Boolean {
        androidContext.dataStore.edit { preferences ->
            preferences[KEEP_SCREEN_ON_KEY] = value
        }
        return true
    }
}