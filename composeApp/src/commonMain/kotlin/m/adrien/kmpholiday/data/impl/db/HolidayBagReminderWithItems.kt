package m.adrien.kmpholiday.data.impl.db

import androidx.room3.Embedded
import androidx.room3.Relation

data class HolidayBagReminderWithItems(
    @Embedded val holiday: HolidayBagReminderEntity,
    @Relation(parentColumns = ["id"], entityColumns = ["holidayId"])
    val items: List<ItemInBagEntity>,
)
