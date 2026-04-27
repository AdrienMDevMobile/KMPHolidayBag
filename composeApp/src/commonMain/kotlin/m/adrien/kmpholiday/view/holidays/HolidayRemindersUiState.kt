package m.adrien.kmpholiday.view.holidays

sealed interface HolidayRemindersUiState {
    data object Loading : HolidayRemindersUiState
    data class Success(val reminders: List<HolidayReminderPreviewUiState>) : HolidayRemindersUiState
    data class Error(val message: String) : HolidayRemindersUiState
}