package m.adrien.kmpholiday.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import m.adrien.kmpholiday.data.impl.db.HolidayBagReminderDao
import m.adrien.kmpholiday.data.impl.db.HolidayBagReminderDatabaseSeeder
import m.adrien.kmpholiday.data.impl.db.toDomain
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderId
import m.adrien.kmpholiday.domain.ItemInBag
import m.adrien.kmpholiday.domain.ItemInBagId
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderRepository

class HolidayBagReminderRepositoryImpl(
    private val dao: HolidayBagReminderDao,
    private val seeder: HolidayBagReminderDatabaseSeeder,
) : HolidayBagReminderRepository {

    override fun get(id: HolidayBagReminderId): Flow<HolidayBagReminder> = flow {
        seeder.ensureSeeded()
        emitAll(
            dao.observeHolidayWithItems(id).map {
                it?.toDomain() ?: throw IllegalArgumentException("Holiday reminder with id $id not found")
            }
        )
    }

    override suspend fun checkItemInBag(holidayId: HolidayBagReminderId, itemId: ItemInBagId, checked: Boolean): Boolean {
        seeder.ensureSeeded()
        dao.updateChecked(holidayId, itemId, checked)
        return true
    }

    override suspend fun setHolidayDuration(holidayId: HolidayBagReminderId, duration: Int): Boolean {
        seeder.ensureSeeded()
        dao.updateDuration(holidayId, duration)
        return true
    }

    override suspend fun edit(holidayId: HolidayBagReminderId, itemId: ItemInBagId, newItem: ItemInBag): Boolean {
        //TODO ensure seeded everywhere ?
        seeder.ensureSeeded()
        dao.updateItem(
            holidayId = holidayId,
            itemId = itemId,
            name = newItem.name,
            quantity = newItem.quantity,
            isDurationIndependant = newItem.isDurationIndependant,
        )
        //TODO handle error
        return true
    }

    override suspend fun deleteItem(holidayId: HolidayBagReminderId, itemId: ItemInBagId): Boolean {
        seeder.ensureSeeded()
        dao.deleteItem(holidayId, itemId)
        return true
    }

    override suspend fun resetWithNewDuration(id: HolidayBagReminderId, duration: Int): Boolean {
        seeder.ensureSeeded()
        dao.resetWithNewDuration(id, duration)
        return true
    }
}
