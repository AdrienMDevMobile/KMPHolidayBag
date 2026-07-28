package m.adrien.kmpholiday.view.holidayBag.value

import m.adrien.kmpholiday.view.shared.NavigationEvent

sealed class HolidayBagNavigationEvent: NavigationEvent() {
    data object NavigateBack : HolidayBagNavigationEvent()
}

// Separate state for navigation events
data class NavigationState(
    val events: List<HolidayBagNavigationEvent> = emptyList()
)

sealed class HolidayBagReminderUiState {
    data object Loading : HolidayBagReminderUiState()

    data class Value(
        val name: String,
        val durationDay: Int,
        val items: List<ItemInBagUiState>,
        val isEditingOn: Boolean = false
    ) : HolidayBagReminderUiState()

    data class Error(val message: String) : HolidayBagReminderUiState()
}