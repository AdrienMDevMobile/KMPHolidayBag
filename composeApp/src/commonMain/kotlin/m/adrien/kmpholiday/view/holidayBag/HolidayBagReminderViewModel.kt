package m.adrien.kmpholiday.view.holidayBag

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import m.adrien.kmpholiday.domain.ItemInBag
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderRepository
import m.adrien.kmpholiday.domain.repository.SettingsRepository
import m.adrien.kmpholiday.view.holidayBag.value.HolidayBagNavigationEvent
import m.adrien.kmpholiday.view.holidayBag.value.HolidayBagReminderUiState
import m.adrien.kmpholiday.view.holidayBag.value.InitializeBagDialogUiState
import m.adrien.kmpholiday.view.holidayBag.value.toUiState

class HolidayBagReminderViewModel(
    val holidayRepository: HolidayBagReminderRepository,
    settingsRepository: SettingsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    private val holidayId = savedStateHandle.get<String>("holidayId") ?: ""

    private var isEditingOn: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val showFixedQtyExplanation: StateFlow<Boolean>
        get() = _showFixedQtyExplanation
    private val _showFixedQtyExplanation: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val initializeBagDialogUiState: StateFlow<InitializeBagDialogUiState?>
        get() = _initializeBagDialogUiState
    private val _initializeBagDialogUiState: MutableStateFlow<InitializeBagDialogUiState?> =
        MutableStateFlow(null)

    val navigationEvents: StateFlow<List<HolidayBagNavigationEvent>>
        get() = _navigationEvents
    private val _navigationEvents = MutableStateFlow<List<HolidayBagNavigationEvent>>(emptyList())

    val keepScreenOn: StateFlow<Boolean> = settingsRepository.get()
        .map { settings -> settings.keepScreenOn }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    // Combine the repository data with editing state only
    val uiState: StateFlow<HolidayBagReminderUiState> =
        holidayRepository.get(holidayId)
            .combine(isEditingOn) { data, editingOn -> data.toUiState(editingOn) }
            .onStart { emit(HolidayBagReminderUiState.Loading) }
            .catch { emit(HolidayBagReminderUiState.Error(it.message ?: "Unknown error")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HolidayBagReminderUiState.Loading
            )

    fun toggleEditMode() {
        isEditingOn.value = !isEditingOn.value
    }

    fun toggleShowFixedQtyExplanation() {
        _showFixedQtyExplanation.value = !_showFixedQtyExplanation.value
    }

    fun toggleItemChecked(itemId: String, checked: Boolean) {
        viewModelScope.launch {
            holidayRepository.checkItemInBag(holidayId, itemId, checked)
        }
    }

    fun updateItemQuantity(itemId: String, quantity: Int) {
        editItem(itemId) { it.copy(quantity = quantity) }
    }

    fun updateItemDurationIndependant(itemId: String, isDurationIndependant: Boolean) {
        editItem(itemId) { it.copy(isDurationIndependant = isDurationIndependant) }
    }

    fun deleteItem(itemId: String) {
        viewModelScope.launch {
            holidayRepository.deleteItem(holidayId, itemId)
        }
    }

    private fun editItem(itemId: String, transform: (ItemInBag) -> ItemInBag) {
        viewModelScope.launch {
            val currentItem = holidayRepository.get(holidayId).first().items.find { it.id == itemId }
                ?: return@launch
            holidayRepository.edit(holidayId, itemId, transform(currentItem))
        }
    }

    fun changeHolidayDuration(duration: Int) {
        viewModelScope.launch {
            holidayRepository.setHolidayDuration(holidayId, duration)
        }
    }

    fun reinitializeHolidayWithDuration() {
        viewModelScope.launch {
            val currentState = _initializeBagDialogUiState.value ?: return@launch
            val durationString =
                currentState.duration.ifBlank { currentState.durationDefault.toString() }
            val error = getDurationGetErrorCode(durationString)
            if (error == null) {

                holidayRepository.resetWithNewDuration(
                    holidayId,
                    durationString.toInt()
                )
                _initializeBagDialogUiState.value = null
            } else {
                _initializeBagDialogUiState.value = currentState?.copy(
                    error = error
                )
            }
        }
    }

    private fun getDurationGetErrorCode(duration: String): String? {
        val durationInt = duration.toIntOrNull()

        return if (durationInt == null) {
            "Please enter a valid number"
        } else if (durationInt < 0) {
            "Duration cannot be negative"
        } else {
            null
        }
    }

    fun updateInitializeDialogDuration(duration: String) {
        if (duration.isBlank()) {
            //No error if blank : take previous iteration's duration as default value
            return
        }

        val error = getDurationGetErrorCode(duration)

        _initializeBagDialogUiState.value = _initializeBagDialogUiState.value?.copy(
            duration = duration,
            error = error
        )
    }

    fun showInitializeDialog(currentDuration: Int) {
        _initializeBagDialogUiState.value = InitializeBagDialogUiState(
            durationDefault = currentDuration,
        )
    }

    fun hideInitializeDialog() {
        _initializeBagDialogUiState.value = null
    }

    fun onBackPressed() {
        // Emit navigation event as a one-time event
        viewModelScope.launch {
            _navigationEvents.update { currentEvents ->
                currentEvents + HolidayBagNavigationEvent.NavigateBack
            }

        }
    }

    fun onNavigationEventProcessed(eventId: String) {
        _navigationEvents.value.filterNot { navigationEvent -> navigationEvent.id == eventId }
    }

}


