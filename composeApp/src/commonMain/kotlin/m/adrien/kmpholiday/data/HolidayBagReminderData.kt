package m.adrien.kmpholiday.data

data class HolidayBagReminderData(
    val name: String,
    val id: String,
    val duration: Int,
    val items: List<ItemInBagData>,
)