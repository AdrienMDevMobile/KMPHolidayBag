package m.adrien.kmpholiday.view.holidays

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import m.adrien.kmpholiday.domain.repository.HolidayReminderPreviewsRepository

class HolidaysViewModel(
    repository: HolidayReminderPreviewsRepository
) : ViewModel() {
    val uiState: StateFlow<HolidayRemindersUiState> = repository.get().map { list ->
        HolidayRemindersUiState.Success(
            list.map { it.toUi() }
        )
    }.stateIn(
            scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HolidayRemindersUiState.Loading
        )

}