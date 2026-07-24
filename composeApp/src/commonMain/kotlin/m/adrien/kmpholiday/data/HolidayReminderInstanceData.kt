package m.adrien.kmpholiday.data

data class HolidayReminderInstanceData(
    val id: String,
    val duration: Int,
    val itemChecked: List<ItemInBagId>
)