package m.adrien.kmpholiday.data.impl.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import m.adrien.kmpholiday.data.HolidayReminderInstanceData

class DataStoreHolidayBagReminderInfosInstanceCache(private val context: Context) :
    HolidayBagReminderInfosInstanceCache {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "holiday_reminder_cache")
        private val REMINDER_PREFIX = "holiday_reminder_"
        private val json = Json { encodeDefaults = true }
    }

    override fun getReminderInstance(holidayId: String): HolidayReminderInstanceData? = runBlocking {
        val key = stringPreferencesKey("$REMINDER_PREFIX$holidayId")
        val preferences = context.dataStore.data.first()
        val jsonString = preferences[key]
        return@runBlocking jsonString?.let { json.decodeFromString<HolidayReminderInstanceData>(it) }
    }

    override fun saveReminderInstance(holidayId: String, reminderData: HolidayReminderInstanceData) {
        runBlocking {
            val key = stringPreferencesKey("$REMINDER_PREFIX$holidayId")
            val jsonString = json.encodeToString(reminderData)
            context.dataStore.edit { preferences ->
                preferences[key] = jsonString
            }
        }
    }
}