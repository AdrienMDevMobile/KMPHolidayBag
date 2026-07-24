package m.adrien.kmpholiday.view.holidayBag.value

sealed class HolidayBagReminderUiState {
    data object Loading : HolidayBagReminderUiState()
    
    data class Value(
        val name: String,
        val durationDay: Int,
        val items: List<ItemInBagUiState>,
        val isEditing: Boolean = false
    ) : HolidayBagReminderUiState()
    
    data class Error(val message: String) : HolidayBagReminderUiState()
}