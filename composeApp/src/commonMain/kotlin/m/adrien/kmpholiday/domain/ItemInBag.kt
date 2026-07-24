package m.adrien.kmpholiday.domain

data class ItemInBag(
    val name: String,
    val id: HolidayItemId,
    val checked: Boolean,
    val quantity: Int,
    val isDurationDependant: Boolean
)

typealias HolidayItemId = String