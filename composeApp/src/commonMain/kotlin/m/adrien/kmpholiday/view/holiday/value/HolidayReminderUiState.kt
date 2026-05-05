package m.adrien.kmpholiday.view.holiday.value

data class HolidayReminderUiState(
    val name: String,
    val durationDay: Int,
    val items: List<HolidayItemUiState>
)