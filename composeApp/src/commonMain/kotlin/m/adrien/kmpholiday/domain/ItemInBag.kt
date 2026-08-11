package m.adrien.kmpholiday.domain

data class ItemInBag(
    val name: String,
    val id: ItemInBagId,
    val checked: Boolean,
    val quantity: Int,
    val isDurationIndependant: Boolean
)

typealias ItemInBagId = String