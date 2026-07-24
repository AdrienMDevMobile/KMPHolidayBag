package m.adrien.kmpholiday.domain.repository

import kotlinx.coroutines.flow.Flow
import m.adrien.kmpholiday.domain.ItemInBag
import m.adrien.kmpholiday.domain.HolidayItemId
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderId

interface HolidayReminderRepository {
    fun get(id: HolidayBagReminderId): Flow<HolidayBagReminder>
    suspend fun check(itemId: HolidayItemId): Boolean
    suspend fun edit(itemId: HolidayItemId, newItem: ItemInBag): Boolean
    suspend fun reset(id: HolidayBagReminderId): Boolean
}