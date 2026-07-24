package m.adrien.kmpholiday.domain

data class HolidayBagReminder(
    val id: HolidayBagReminderId,
    val name: String,
    val duration: Int,
    val items: List<ItemInBag>,
)

data class HolidayBagReminderPreview(
    val id: HolidayBagReminderId,
    val name: String,
)

typealias HolidayBagReminderId = String