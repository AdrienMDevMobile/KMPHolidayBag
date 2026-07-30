package m.adrien.kmpholiday.view.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import m.adrien.kmpholiday.domain.repository.SettingsRepository
import m.adrien.kmpholiday.view.settings.value.SettingsNavigationEvent

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    val navigationEvents: StateFlow<List<SettingsNavigationEvent>>
        get() = _navigationEvents
    private val _navigationEvents = MutableStateFlow<List<SettingsNavigationEvent>>(emptyList())

    init {
        viewModelScope.launch {
            settingsRepository.get().collect { settings ->
                _uiState.update { currentState ->
                    currentState.copy(keepScreenOn = settings.keepScreenOn)
                }
            }
        }
    }

    fun toggleKeepScreenOn() {
        viewModelScope.launch {
            val newValue = !_uiState.value.keepScreenOn
            settingsRepository.setKeepScreenOn(newValue)
            // The repository will update the flow, which will be collected in init
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