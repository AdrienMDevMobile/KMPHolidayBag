package m.adrien.kmpholiday.data.impl.db

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import m.adrien.kmpholiday.data.impl.HolidayBagReminderPreviewsRepositoryImpl
import m.adrien.kmpholiday.data.impl.HolidayBagReminderRepositoryImpl
import m.adrien.kmpholiday.data.impl.StaticDatas
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HolidayBagReminderRepositoryTest {

    private fun newInMemoryDb(): AppDatabase =
        Room.inMemoryDatabaseBuilder<AppDatabase>()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()

    @Test
    fun seeding_populates_trips_from_static_data_when_db_is_empty() = runTest {
        val dao = newInMemoryDb().holidayBagReminderDao()
        val seeder = HolidayBagReminderDatabaseSeeder(dao)

        seeder.ensureSeeded()

        assertEquals(StaticDatas.listOfHolidayBagReminder.size, dao.countHolidays())
    }

    @Test
    fun seeding_does_not_double_insert_when_called_twice() = runTest {
        val dao = newInMemoryDb().holidayBagReminderDao()
        val seeder = HolidayBagReminderDatabaseSeeder(dao)

        seeder.ensureSeeded()
        seeder.ensureSeeded()

        assertEquals(StaticDatas.listOfHolidayBagReminder.size, dao.countHolidays())
    }

    @Test
    fun get_returns_merged_domain_object_matching_static_template() = runTest {
        val dao = newInMemoryDb().holidayBagReminderDao()
        val repository = HolidayBagReminderRepositoryImpl(dao, HolidayBagReminderDatabaseSeeder(dao))

        val expected = StaticDatas.listOfHolidayBagReminder.first()
        val result = repository.get(expected.id).first()

        assertEquals(expected.name, result.name)
        assertEquals(expected.items.size, result.items.size)
        assertTrue(result.items.none { it.checked })
    }

    @Test
    fun checkItemInBag_and_setHolidayDuration_persist_and_are_reflected_in_subsequent_get() = runTest {
        val dao = newInMemoryDb().holidayBagReminderDao()
        val repository = HolidayBagReminderRepositoryImpl(dao, HolidayBagReminderDatabaseSeeder(dao))

        val holiday = StaticDatas.listOfHolidayBagReminder.first()
        val itemId = repository.get(holiday.id).first().items.first().id

        repository.checkItemInBag(holiday.id, itemId, checked = true)
        repository.setHolidayDuration(holiday.id, duration = 5)

        val result = repository.get(holiday.id).first()
        assertEquals(5, result.duration)
        assertTrue(result.items.first { it.id == itemId }.checked)
    }

    @Test
    fun resetWithNewDuration_clears_checked_items_and_sets_new_duration() = runTest {
        val dao = newInMemoryDb().holidayBagReminderDao()
        val repository = HolidayBagReminderRepositoryImpl(dao, HolidayBagReminderDatabaseSeeder(dao))

        val holiday = StaticDatas.listOfHolidayBagReminder.first()
        val itemId = repository.get(holiday.id).first().items.first().id
        repository.checkItemInBag(holiday.id, itemId, checked = true)

        repository.resetWithNewDuration(holiday.id, duration = 7)

        val result = repository.get(holiday.id).first()
        assertEquals(7, result.duration)
        assertTrue(result.items.none { it.checked })
    }

    @Test
    fun edit_persists_new_quantity_and_duration_independant_flag() = runTest {
        val dao = newInMemoryDb().holidayBagReminderDao()
        val repository = HolidayBagReminderRepositoryImpl(dao, HolidayBagReminderDatabaseSeeder(dao))

        val holiday = StaticDatas.listOfHolidayBagReminder.first()
        val item = repository.get(holiday.id).first().items.first()

        repository.edit(
            holiday.id,
            item.id,
            item.copy(quantity = item.quantity + 3, isDurationIndependant = !item.isDurationIndependant)
        )

        val result = repository.get(holiday.id).first().items.first { it.id == item.id }
        assertEquals(item.quantity + 3, result.quantity)
        assertEquals(!item.isDurationIndependant, result.isDurationIndependant)
    }

    @Test
    fun deleteItem_removes_item_and_is_reflected_in_subsequent_get() = runTest {
        val dao = newInMemoryDb().holidayBagReminderDao()
        val repository = HolidayBagReminderRepositoryImpl(dao, HolidayBagReminderDatabaseSeeder(dao))

        val holiday = StaticDatas.listOfHolidayBagReminder.first()
        val itemId = repository.get(holiday.id).first().items.first().id
        val itemCountBefore = repository.get(holiday.id).first().items.size

        repository.deleteItem(holiday.id, itemId)

        val result = repository.get(holiday.id).first()
        assertEquals(itemCountBefore - 1, result.items.size)
        assertFalse(result.items.any { it.id == itemId })
    }

    @Test
    fun previews_repository_lists_all_seeded_trips() = runTest {
        val dao = newInMemoryDb().holidayBagReminderDao()
        val repository = HolidayBagReminderPreviewsRepositoryImpl(dao, HolidayBagReminderDatabaseSeeder(dao))

        val previews = repository.get().first()

        assertEquals(StaticDatas.listOfHolidayBagReminder.map { it.id }.toSet(), previews.map { it.id }.toSet())
    }
}
