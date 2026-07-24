package m.adrien.kmpholiday.view.holiday.value

sealed class HolidayBagReminderUiState {
    data object Loading : HolidayBagReminderUiState()
    
    data class Value(
        val name: String,
        val durationDay: Int,
        val items: List<ItemInBagUiState>
    ) : HolidayBagReminderUiState()
    
    data class Error(val message: String) : HolidayBagReminderUiState()
}