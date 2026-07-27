package m.adrien.kmpholiday.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import m.adrien.kmpholiday.data.impl.cache.HolidayBagReminderCacheFactory
import m.adrien.kmpholiday.data.impl.cache.HolidayBagReminderInfosInstanceCache
import m.adrien.kmpholiday.domain.ItemInBag
import m.adrien.kmpholiday.domain.ItemInBagId
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderId
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderRepository

class HolidayBagReminderRepositoryImpl(
    private val holidayReminderInstanceCache: HolidayBagReminderInfosInstanceCache = HolidayBagReminderCacheFactory.createCache()
) : HolidayBagReminderRepository {

    override fun get(id: HolidayBagReminderId): Flow<HolidayBagReminder> = flow {
        val staticData = StaticDatas.listOfHolidayBagReminder.find { it.id == id }

        if (staticData != null) {
            // Combine static data with dynamic cache data
            holidayReminderInstanceCache.getReminderInstance(id).collect { cachedData ->
                val duration = cachedData?.duration ?: 0 // Default duration if not cached
                val checkedItems = cachedData?.itemChecked ?: emptyList()

                val holidayBagReminder = HolidayBagReminder(
                    id = staticData.id,
                    name = staticData.name,
                    duration = duration,
                    items = staticData.items.map { itemData ->
                        ItemInBag(
                            name = itemData.name,
                            id = itemData.id,
                            checked = itemData.id in checkedItems, // Use cached checked state
                            quantity = itemData.quantity,
                            isDurationDependant = itemData.isDayDependant
                        )
                    }
                )

                emit(holidayBagReminder)
            }
        } else {
            throw IllegalArgumentException("Holiday reminder with id $id not found")
        }
    }

    override suspend fun checkItemInBag(holidayId: HolidayBagReminderId, itemId: ItemInBagId, checked: Boolean): Boolean {
        holidayReminderInstanceCache.checkItem(holidayId, itemId, checked)
        return true //TODO
    }

    override suspend fun setHolidayDuration(holidayId: HolidayBagReminderId, duration: Int): Boolean {
        holidayReminderInstanceCache.setReminderInstanceDuration(holidayId, duration)
        return true
    }

    override suspend fun edit(itemId: ItemInBagId, newItem: ItemInBag): Boolean {
        // Dans notre architecture mixte, les items sont statiques et ne peuvent pas être édités
        // Cette méthode pourrait être implémentée pour permettre l'édition des items
        // Pour l'instant, nous retournons false car nous ne supportons pas cette fonctionnalité
        return false
    }

    override suspend fun resetWithNewDuration(id: HolidayBagReminderId, duration: Int): Boolean {
        holidayReminderInstanceCache.emptyHolidayBagWithNewDuration(id, duration)
        return true
    }
}