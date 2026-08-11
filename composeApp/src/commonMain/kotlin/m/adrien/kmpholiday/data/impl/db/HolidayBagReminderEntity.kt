package m.adrien.kmpholiday.data.impl.db

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "holiday_bag_reminder")
data class HolidayBagReminderEntity(
    @PrimaryKey val id: String,
    val name: String,
    val duration: Int = 0,
)
