package m.adrien.kmpholiday.domain

data class HolidayItem(
    val name: String,
    val id: HolidayItemId,
    val quantity: Int,
    val isDayDependant: Boolean
)

typealias HolidayItemId = String