package m.adrien.kmpholiday.view.holidayBag.value

sealed class ItemInBagUiState {
    abstract val id: String
    abstract val name: String

    data class CheckMode(
        override val id: String,
        override val name: String,
        val checked: Boolean,
        val quantity: Int,
    ) : ItemInBagUiState()

    data class EditMode(
        override val id: String,
        override val name: String,
        val quantity: Int,
        val isDurationIndependant: Boolean,
    ) : ItemInBagUiState()
}
