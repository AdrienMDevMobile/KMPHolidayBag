package m.adrien.kmpholiday.data.impl.db

import kotlin.concurrent.Volatile
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import m.adrien.kmpholiday.data.impl.StaticDatas

class HolidayBagReminderDatabaseSeeder(private val dao: HolidayBagReminderDao) {
    private val mutex = Mutex()

    @Volatile
    private var seeded = false

    suspend fun ensureSeeded() {
        if (seeded) return
        mutex.withLock {
            if (seeded) return
            if (dao.countHolidays() == 0) {
                val holidays = StaticDatas.listOfHolidayBagReminder.map { it.toEntity() }
                val items = StaticDatas.listOfHolidayBagReminder.flatMap { holiday ->
                    holiday.items.mapIndexed { index, item ->
                        item.toEntity(holidayId = holiday.id, itemId = index.toString())
                    }
                }
                dao.seedAll(holidays, items)
            }
            seeded = true
        }
    }
}
