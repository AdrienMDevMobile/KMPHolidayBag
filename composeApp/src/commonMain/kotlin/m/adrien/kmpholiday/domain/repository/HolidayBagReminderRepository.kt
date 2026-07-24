package m.adrien.kmpholiday.domain.repository

import kotlinx.coroutines.flow.Flow
import m.adrien.kmpholiday.domain.ItemInBag
import m.adrien.kmpholiday.domain.ItemInBagId
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderId

interface HolidayBagReminderRepository {
    fun get(id: HolidayBagReminderId): Flow<HolidayBagReminder>
    suspend fun check(itemId: ItemInBagId): Boolean
    suspend fun edit(itemId: ItemInBagId, newItem: ItemInBag): Boolean
    suspend fun reset(id: HolidayBagReminderId): Boolean
}