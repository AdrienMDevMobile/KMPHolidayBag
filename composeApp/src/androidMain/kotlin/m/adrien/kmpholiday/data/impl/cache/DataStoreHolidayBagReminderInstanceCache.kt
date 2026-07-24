package m.adrien.kmpholiday.data.impl.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class DataStoreHolidayBagReminderInstanceCache(private val context: Context) :
    HolidayBagReminderInstanceCache {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "holiday_reminder_cache")
        private val DURATION_PREFIX = "holiday_duration_"
    }

    override fun getReminderInstance(holidayId: String): Int? = runBlocking {
        val key = intPreferencesKey("$DURATION_PREFIX$holidayId")
        val preferences = context.dataStore.data.first()
        return@runBlocking preferences[key]
    }

    override fun saveReminderInstance(holidayId: String, duration: Int) {
        runBlocking {
            val key = intPreferencesKey("$DURATION_PREFIX$holidayId")
            context.dataStore.edit { preferences ->
                preferences[key] = duration
            }
        }
    }
}