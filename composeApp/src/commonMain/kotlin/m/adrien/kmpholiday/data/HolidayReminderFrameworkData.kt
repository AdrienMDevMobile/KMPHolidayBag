package m.adrien.kmpholiday.data

data class HolidayReminderFrameworkData(
    val name: String,
    val id: String,
    val duration: Int = 0, // Default duration
    val items: List<ItemInBagData>,
)