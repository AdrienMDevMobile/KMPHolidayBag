package m.adrien.kmpholiday.data

data class HolidayReminderData(
    val name: String,
    val id: String,
    val items: List<HolidayItemData>,
)