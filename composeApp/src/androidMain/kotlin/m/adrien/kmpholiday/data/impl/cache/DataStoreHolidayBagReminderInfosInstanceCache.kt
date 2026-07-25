package m.adrien.kmpholiday.data.impl.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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

    override fun getReminderInstance(holidayId: String): Flow<HolidayReminderInstanceData?> {
        val key = stringPreferencesKey("$REMINDER_PREFIX$holidayId")
        return context.dataStore.data.map { preferences ->
            val jsonString = preferences[key]
            jsonString?.let { json.decodeFromString<HolidayReminderInstanceData>(it) }
        }
    }

    override suspend fun saveReminderInstance(holidayId: String, reminderData: HolidayReminderInstanceData) {
        val key = stringPreferencesKey("$REMINDER_PREFIX$holidayId")
        val jsonString = json.encodeToString(reminderData)
        context.dataStore.edit { preferences ->
            preferences[key] = jsonString
        }
    }

    override suspend fun setReminderInstanceDuration(holidayId: String, duration: Int) {
        val key = stringPreferencesKey("$REMINDER_PREFIX$holidayId")
        val currentData = context.dataStore.data.first()
        val currentJson = currentData[key]
        
        val currentReminder = currentJson?.let { json.decodeFromString<HolidayReminderInstanceData>(it) }
            ?: HolidayReminderInstanceData(holidayId, 0, emptyList())
        
        val updatedReminder = currentReminder.copy(duration = duration)
        val updatedJson = json.encodeToString(updatedReminder)
        
        context.dataStore.edit { preferences ->
            preferences[key] = updatedJson
        }
    }

    override suspend fun checkItem(
        holidayId: String,
        itemId: String,
        checked: Boolean
    ) {
        val key = stringPreferencesKey("$REMINDER_PREFIX$holidayId")
        val currentData = context.dataStore.data.first()
        val currentJson = currentData[key]
        
        val currentReminder = currentJson?.let { json.decodeFromString<HolidayReminderInstanceData>(it) }
            ?: HolidayReminderInstanceData(holidayId, 0, emptyList())
        
        val updatedCheckedItems = if (checked) {
            // Add item to checked list if not already present
            if (itemId !in currentReminder.itemChecked) {
                currentReminder.itemChecked + itemId
            } else {
                currentReminder.itemChecked
            }
        } else {
            // Remove item from checked list if present
            currentReminder.itemChecked.filterNot { it == itemId }
        }
        
        val updatedReminder = currentReminder.copy(itemChecked = updatedCheckedItems)
        val updatedJson = json.encodeToString(updatedReminder)
        
        context.dataStore.edit { preferences ->
            preferences[key] = updatedJson
        }
    }

    override suspend fun resetHoliday(holidayId: String) {
        val key = stringPreferencesKey("$REMINDER_PREFIX$holidayId")
        // Delete all data for this holiday by removing the preference key
        context.dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }
}