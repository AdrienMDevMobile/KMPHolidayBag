package m.adrien.kmpholiday.data.converter

import m.adrien.kmpholiday.data.HolidayBagReminderData
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderPreview

fun HolidayBagReminderData.toDomain(): HolidayBagReminder {
    return HolidayBagReminder(
        id = this.id,
        name = this.name,
        duration = duration,
        items = this.items.map { it.toDomain() }
    )
}

fun HolidayBagReminder.toData(): HolidayBagReminderData {
    return HolidayBagReminderData(
        id = this.id,
        name = this.name,
        duration = duration,
        items = this.items.map { it.toData() }
    )
}

fun HolidayBagReminderData.toPreview(): HolidayBagReminderPreview {
    return HolidayBagReminderPreview(
        name = this.name,
        id = this.id
    )
}

fun HolidayBagReminder.toPreview(): HolidayBagReminderPreview {
    return HolidayBagReminderPreview(
        name = this.name,
        id = this.id
    )
}