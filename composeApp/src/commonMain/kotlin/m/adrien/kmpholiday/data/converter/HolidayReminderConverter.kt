package m.adrien.kmpholiday.data.converter

import m.adrien.kmpholiday.data.HolidayReminderData
import m.adrien.kmpholiday.domain.HolidayReminder
import m.adrien.kmpholiday.domain.HolidayReminderPreview

fun HolidayReminderData.toDomain(): HolidayReminder {
    return HolidayReminder(
        id = this.id,
        name = this.name,
        duration = duration,
        items = this.items.map { it.toDomain() }
    )
}

fun HolidayReminder.toData(): HolidayReminderData {
    return HolidayReminderData(
        id = this.id,
        name = this.name,
        duration = duration,
        items = this.items.map { it.toData() }
    )
}

fun HolidayReminderData.toPreview(): HolidayReminderPreview {
    return HolidayReminderPreview(
        name = this.name,
        id = this.id
    )
}

fun HolidayReminder.toPreview(): HolidayReminderPreview {
    return HolidayReminderPreview(
        name = this.name,
        id = this.id
    )
}