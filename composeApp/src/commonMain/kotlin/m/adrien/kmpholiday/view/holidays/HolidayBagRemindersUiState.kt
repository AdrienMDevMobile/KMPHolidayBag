package m.adrien.kmpholiday.view.holidays

sealed interface HolidayBagRemindersUiState {
    data object Loading : HolidayBagRemindersUiState
    data class Success(val reminders: List<HolidayBagReminderPreviewUiState>) : HolidayBagRemindersUiState
    data class Error(val message: String) : HolidayBagRemindersUiState
}