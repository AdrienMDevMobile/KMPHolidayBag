package m.adrien.kmpholiday.data.impl.db

import m.adrien.kmpholiday.data.HolidayReminderFrameworkData
import m.adrien.kmpholiday.data.ItemInBagData
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderId
import m.adrien.kmpholiday.domain.HolidayBagReminderPreview
import m.adrien.kmpholiday.domain.ItemInBag

fun HolidayReminderFrameworkData.toEntity(): HolidayBagReminderEntity =
    HolidayBagReminderEntity(id = id, name = name, duration = duration)

fun ItemInBagData.toEntity(holidayId: HolidayBagReminderId): ItemInBagEntity =
    ItemInBagEntity(
        holidayId = holidayId,
        itemId = id,
        name = name,
        quantity = quantity,
        isDurationDependant = isDayDependant,
    )

fun HolidayBagReminderWithItems.toDomain(): HolidayBagReminder =
    HolidayBagReminder(
        id = holiday.id,
        name = holiday.name,
        duration = holiday.duration,
        items = items.map { it.toDomain() },
    )

fun ItemInBagEntity.toDomain(): ItemInBag =
    ItemInBag(
        name = name,
        id = itemId,
        checked = checked,
        quantity = quantity,
        isDurationDependant = isDurationDependant,
    )

fun HolidayBagReminderEntity.toPreview(): HolidayBagReminderPreview =
    HolidayBagReminderPreview(id = id, name = name)
