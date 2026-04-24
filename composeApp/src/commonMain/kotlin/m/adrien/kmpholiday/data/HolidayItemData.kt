package m.adrien.kmpholiday.data

data class HolidayItemData(
    val name: String,
    val id: String,
    val quantity: Int,
    val isDayDependant: Boolean
)