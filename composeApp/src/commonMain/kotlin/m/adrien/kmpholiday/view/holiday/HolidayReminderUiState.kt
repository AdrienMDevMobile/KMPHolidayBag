package m.adrien.kmpholiday.view.holiday

data class HolidayReminderUiState(
    val name: String,
    val durationDay: Int,
    val items: List<HolidayItemUiState>
)