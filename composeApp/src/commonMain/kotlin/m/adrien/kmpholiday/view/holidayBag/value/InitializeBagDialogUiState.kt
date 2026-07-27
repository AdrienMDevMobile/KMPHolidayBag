package m.adrien.kmpholiday.view.holidayBag.value

data class InitializeBagDialogUiState(
    val durationDefault: Int,
    val duration: String = "",
    val error: String? = null,
)