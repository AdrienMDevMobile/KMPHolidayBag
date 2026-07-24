package m.adrien.kmpholiday.data

data class ItemInBagData(
    val name: String,
    val id: ItemInBagId,
    val quantity: Int,
    val isDayDependant: Boolean
)

typealias ItemInBagId = String