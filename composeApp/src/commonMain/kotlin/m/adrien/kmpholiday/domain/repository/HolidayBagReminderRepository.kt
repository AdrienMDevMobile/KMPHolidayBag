package m.adrien.kmpholiday.domain.repository

import kotlinx.coroutines.flow.Flow
import m.adrien.kmpholiday.domain.ItemInBag
import m.adrien.kmpholiday.domain.ItemInBagId
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderId

interface HolidayBagReminderRepository {
    fun get(id: HolidayBagReminderId): Flow<HolidayBagReminder>
    suspend fun checkItemInBag(holidayId: HolidayBagReminderId, itemId: ItemInBagId, checked: Boolean): Boolean
    suspend fun setHolidayDuration(holidayId: HolidayBagReminderId, duration: Int): Boolean
    suspend fun edit(holidayId: HolidayBagReminderId, itemId: ItemInBagId, newItem: ItemInBag): Boolean
    suspend fun deleteItem(holidayId: HolidayBagReminderId, itemId: ItemInBagId): Boolean
    suspend fun resetWithNewDuration(id: HolidayBagReminderId, duration: Int): Boolean
}