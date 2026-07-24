package m.adrien.kmpholiday.data.converter

import m.adrien.kmpholiday.data.HolidayReminderFrameworkData
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderPreview

fun HolidayReminderFrameworkData.toDomain(): HolidayBagReminder {
    return HolidayBagReminder(
        id = this.id,
        name = this.name,
        duration = duration,
        items = this.items.map { it.toDomain() }
    )
}

fun HolidayBagReminder.toData(): HolidayReminderFrameworkData {
    return HolidayReminderFrameworkData(
        id = this.id,
        name = this.name,
        duration = duration,
        items = this.items.map { it.toData() }
    )
}

fun HolidayReminderFrameworkData.toPreview(): HolidayBagReminderPreview {
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