package m.adrien.kmpholiday.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import m.adrien.kmpholiday.data.impl.db.HolidayBagReminderDao
import m.adrien.kmpholiday.data.impl.db.HolidayBagReminderDatabaseSeeder
import m.adrien.kmpholiday.data.impl.db.toPreview
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderPreviews
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderPreviewsRepository

class HolidayBagReminderPreviewsRepositoryImpl(
    private val dao: HolidayBagReminderDao,
    private val seeder: HolidayBagReminderDatabaseSeeder,
) : HolidayBagReminderPreviewsRepository {

    override fun get(): Flow<HolidayBagReminderPreviews> = flow {
        seeder.ensureSeeded()
        emitAll(dao.observeAllHolidays().map { holidays -> holidays.map { it.toPreview() } })
    }

    override suspend fun create(holidayBagReminder: HolidayBagReminder): Boolean {
        TODO("Not yet implemented")
    }
}
