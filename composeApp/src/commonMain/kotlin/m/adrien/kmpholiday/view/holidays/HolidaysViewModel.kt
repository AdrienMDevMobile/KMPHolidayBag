package m.adrien.kmpholiday.view.holidays

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderPreviewsRepository
import m.adrien.kmpholiday.view.holidays.value.HolidaysNavigationEvent

class HolidaysViewModel(
    repository: HolidayBagReminderPreviewsRepository
) : ViewModel() {
    val uiState: StateFlow<HolidayBagRemindersUiState> = repository.get().map { list ->
        HolidayBagRemindersUiState.Success(
            list.map { it.toUi() }
        )
    }.stateIn(
            scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HolidayBagRemindersUiState.Loading
        )

    val navigationEvents: StateFlow<List<HolidaysNavigationEvent>>
        get() = _navigationEvents
    private val _navigationEvents = MutableStateFlow<List<HolidaysNavigationEvent>>(emptyList())

    fun onHolidayClick(holidayId: String) {
        viewModelScope.launch {
            _navigationEvents.update { currentEvents ->
                currentEvents + HolidaysNavigationEvent.NavigateToHoliday(holidayId)
            }
        }
    }

    fun onNavigationEventProcessed(eventId: String) {
        _navigationEvents.value = _navigationEvents.value.filterNot { navigationEvent ->
            navigationEvent.id == eventId
        }
    }
}