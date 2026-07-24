package m.adrien.kmpholiday.data.impl.cache

import kotlinx.coroutines.flow.Flow
import m.adrien.kmpholiday.data.HolidayReminderInstanceData

interface HolidayBagReminderInfosInstanceCache {
    fun getReminderInstance(holidayId: String): Flow<HolidayReminderInstanceData?>
    suspend fun saveReminderInstance(holidayId: String, reminderData: HolidayReminderInstanceData)
    suspend fun setReminderInstanceDuration(holidayId: String, duration: Int)
    suspend fun checkItem(holidayId: String, itemId: String, checked: Boolean)
}