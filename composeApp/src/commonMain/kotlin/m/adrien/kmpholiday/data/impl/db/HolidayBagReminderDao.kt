package m.adrien.kmpholiday.data.impl.db

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
//stick to one DAO : concentrates all the code in one element which is not the best,
// but limits the complexity of many functions
interface HolidayBagReminderDao {

    @Transaction
    @Query("SELECT * FROM holiday_bag_reminder WHERE id = :id")
    fun observeHolidayWithItems(id: String): Flow<HolidayBagReminderWithItems?>

    @Query("SELECT * FROM holiday_bag_reminder ORDER BY name")
    fun observeAllHolidays(): Flow<List<HolidayBagReminderEntity>>

    @Query("SELECT COUNT(*) FROM holiday_bag_reminder")
    suspend fun countHolidays(): Int

    @Insert
    suspend fun insertHolidays(holidays: List<HolidayBagReminderEntity>)

    @Insert
    suspend fun insertItems(items: List<ItemInBagEntity>)

    @Query("UPDATE holiday_bag_reminder SET duration = :duration WHERE id = :holidayId")
    suspend fun updateDuration(holidayId: String, duration: Int)

    @Query("UPDATE item_in_bag SET checked = :checked WHERE holidayId = :holidayId AND itemId = :itemId")
    suspend fun updateChecked(holidayId: String, itemId: String, checked: Boolean)

    @Query(
        "UPDATE item_in_bag SET name = :name, quantity = :quantity, isDurationIndependant = :isDurationIndependant " +
            "WHERE holidayId = :holidayId AND itemId = :itemId"
    )
    suspend fun updateItem(
        holidayId: String,
        itemId: String,
        name: String,
        quantity: Int,
        isDurationIndependant: Boolean
    )

    @Query("DELETE FROM item_in_bag WHERE holidayId = :holidayId AND itemId = :itemId")
    suspend fun deleteItem(holidayId: String, itemId: String)

    @Query("UPDATE item_in_bag SET checked = 0 WHERE holidayId = :holidayId")
    suspend fun clearChecked(holidayId: String)

    @Transaction
    suspend fun resetWithNewDuration(holidayId: String, duration: Int) {
        updateDuration(holidayId, duration)
        clearChecked(holidayId)
    }

    @Transaction
    suspend fun seedAll(holidays: List<HolidayBagReminderEntity>, items: List<ItemInBagEntity>) {
        insertHolidays(holidays)
        insertItems(items)
    }
}
