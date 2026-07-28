package m.adrien.kmpholiday.view.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

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
}

data class SettingsUiState(
    val keepScreenOn: Boolean = false,
    val showTooltip: Boolean = false
)