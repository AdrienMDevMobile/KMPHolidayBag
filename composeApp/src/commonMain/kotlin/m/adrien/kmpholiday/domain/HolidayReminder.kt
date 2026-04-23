package m.adrien.kmpholiday.domain

data class HolidayReminder(
    val name: String,
    val id: HolidayReminderId,
    val items: List<HolidayItem>,
)

data class HolidayReminderPreview(
    val name: String,
    val id: HolidayReminderId,
)

typealias HolidayReminderId = String