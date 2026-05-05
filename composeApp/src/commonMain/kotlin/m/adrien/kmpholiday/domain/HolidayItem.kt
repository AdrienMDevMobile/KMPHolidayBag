package m.adrien.kmpholiday.domain

data class HolidayItem(
    val name: String,
    val id: HolidayItemId,
    val checked: Boolean,
    val quantity: Int,
    val isDurationDependant: Boolean
)

typealias HolidayItemId = String