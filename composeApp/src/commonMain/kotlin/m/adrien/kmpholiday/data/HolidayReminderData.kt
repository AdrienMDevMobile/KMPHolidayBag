package m.adrien.kmpholiday.data

data class HolidayReminderData(
    val name: String,
    val id: String,
    val duration: Int,
    val items: List<HolidayItemData>,
)