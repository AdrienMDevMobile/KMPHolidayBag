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

    override suspend fun edit(itemId: ItemInBagId, newItem: ItemInBag): Boolean {
        //TODO Items are not yet user-editable; on the roadmap but out of scope here.
        return false
    }

    override suspend fun resetWithNewDuration(id: HolidayBagReminderId, duration: Int): Boolean {
        seeder.ensureSeeded()
        dao.resetWithNewDuration(id, duration)
        return true
    }
}
