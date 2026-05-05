package m.adrien.kmpholiday.domain

data class HolidayReminder(
    val id: HolidayReminderId,
    val name: String,
    val duration: Int,
    val items: List<HolidayItem>,
)

data class HolidayReminderPreview(
    val id: HolidayReminderId,
    val name: String,
)

typealias HolidayReminderId = String