package m.adrien.kmpholiday.view.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import m.adrien.kmpholiday.view.settings.value.SettingsNavigationEvent

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    val navigationEvents: StateFlow<List<SettingsNavigationEvent>>
        get() = _navigationEvents
    private val _navigationEvents = MutableStateFlow<List<SettingsNavigationEvent>>(emptyList())

    fun toggleKeepScreenOn() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                keepScreenOn = !_uiState.value.keepScreenOn
            )
        }
    }

    fun toggleShowTooltipKeepScreenOn() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                showTooltip = !_uiState.value.showTooltip
            )
        }
    }

    fun hideTooltip() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                showTooltip = false
            )
        }
    }

    fun onBackPressed() {
        viewModelScope.launch {
            _navigationEvents.update { currentEvents ->
                currentEvents + SettingsNavigationEvent.NavigateBack
            }
        }
    }

    fun onNavigationEventProcessed(eventId: String) {
        _navigationEvents.value = _navigationEvents.value.filterNot { navigationEvent -> navigationEvent.id == eventId }
    }
}

data class SettingsUiState(
    val keepScreenOn: Boolean = false,
    val showTooltip: Boolean = false
)