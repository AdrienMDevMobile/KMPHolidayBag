package m.adrien.kmpholiday.view.holiday

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import m.adrien.kmpholiday.domain.repository.HolidayReminderRepository
import m.adrien.kmpholiday.view.holiday.value.HolidayItemUiState
import m.adrien.kmpholiday.view.holiday.value.HolidayReminderUiState
import m.adrien.kmpholiday.view.holiday.value.toUiState

class HolidayReminderViewModel(
    holidayRepository: HolidayReminderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    private val holidayId = savedStateHandle.get<String>("holidayId") ?: ""

    // UI State
    private val uiState: StateFlow<HolidayReminderUiState> =
        holidayRepository.get(holidayId)
            .map { data -> data.toUiState() }
            .onStart { emit(HolidayReminderUiState.Loading) }
            .catch { emit(HolidayReminderUiState.Error(it.message ?: "Unknown error")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HolidayReminderUiState.Loading
            )

    // Editing state
    var isEditing by mutableStateOf(false)
        private set

    fun toggleEditMode() {
        isEditing = !isEditing
    }

    /*
    fun updateHolidayName(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
        holidayRepository.saveHolidayData(_uiState.value.toDomainModel())
    }

    fun updateDuration(newDuration: Int) {
        _uiState.value = _uiState.value.copy(durationDay = newDuration)
        // Recalculate item quantities based on new duration
        updateItemQuantitiesForDuration()
        holidayRepository.saveHolidayData(_uiState.value.toDomainModel())
    }

    fun addItem(itemName: String, baseQuantity: Int, isDurationDependent: Boolean) {
        val calculatedQuantity = if (isDurationDependent) {
            durationCalculator.calculateQuantity(baseQuantity, _uiState.value.durationDay)
        } else {
            baseQuantity
        }

        val newItem = HolidayItemUiState(
            name = itemName,
            checked = false,
            quantity = calculatedQuantity,
            isDurationDependent = isDurationDependent
        )

        val updatedItems = _uiState.value.items.toMutableList().apply {
            add(newItem)
        }
        updateItems(updatedItems)
    }

    fun removeItem(itemName: String) {
        val updatedItems = _uiState.value.items.filterNot { it.name == itemName }
        updateItems(updatedItems)
    }

    fun toggleItemChecked(itemName: String) {
        val updatedItems = _uiState.value.items.map { item ->
            if (item.name == itemName) {
                item.copy(checked = !item.checked)
            } else {
                item
            }
        }.sortedBy { it.checked } // Move checked items to bottom

        updateItems(updatedItems)
    }

    private fun updateItemQuantitiesForDuration() {
        val updatedItems = _uiState.value.items.map { item ->
            if (item.isDurationDependent) {
                val baseQuantity = item.quantity / _uiState.value.durationDay
                val newQuantity =
                    durationCalculator.calculateQuantity(baseQuantity, _uiState.value.durationDay)
                item.copy(quantity = newQuantity)
            } else {
                item
            }
        }

        updateItems(updatedItems)
    }
*/
    private fun updateItems(updatedItems: List<HolidayItemUiState>) {
        /*
        _uiState.value = _uiState.value.copy(items = updatedItems)
        holidayRepository.saveHolidayData(_uiState.value.toDomainModel())
         */
    }
}


