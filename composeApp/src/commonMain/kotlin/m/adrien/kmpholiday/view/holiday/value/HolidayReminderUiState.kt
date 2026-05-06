package m.adrien.kmpholiday.view.holiday.value

sealed class HolidayReminderUiState {
    data object Loading : HolidayReminderUiState()
    
    data class Value(
        val name: String,
        val durationDay: Int,
        val items: List<HolidayItemUiState>
    ) : HolidayReminderUiState()
    
    data class Error(val message: String) : HolidayReminderUiState()
}