package m.adrien.kmpholiday.domain.repository

import kotlinx.coroutines.flow.Flow
import m.adrien.kmpholiday.domain.HolidayItem
import m.adrien.kmpholiday.domain.HolidayItemId
import m.adrien.kmpholiday.domain.HolidayReminder
import m.adrien.kmpholiday.domain.HolidayReminderId

interface HolidayReminderRepository {
    fun get(id: HolidayReminderId): Flow<HolidayReminder>
    suspend fun check(itemId: HolidayItemId): Boolean
    suspend fun edit(itemId: HolidayItemId, newItem: HolidayItem): Boolean
    suspend fun reset(id: HolidayReminderId): Boolean
}