package m.adrien.kmpholiday.view.holidayBag

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.createSavedStateHandle
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderRepository
import m.adrien.kmpholiday.view.holidayBag.value.ItemInBagUiState
import m.adrien.kmpholiday.view.holidayBag.value.HolidayBagReminderUiState
import m.adrien.kmpholiday.view.holidayBag.value.toUiState
class HolidayBagReminderViewModel(
    val holidayRepository: HolidayBagReminderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    private val holidayId = savedStateHandle.get<String>("holidayId") ?: ""

    // Editing state
    private var isEditing by mutableStateOf(false)

    val uiState: StateFlow<HolidayBagReminderUiState> =
        holidayRepository.get(holidayId)
            .map { data -> 
                val baseUiState = data.toUiState()
                if (baseUiState is HolidayBagReminderUiState.Value) {
                    baseUiState.copy(isEditing = isEditing)
                } else {
                    baseUiState
                }
            }
            .onStart { emit(HolidayBagReminderUiState.Loading) }
            .catch { emit(HolidayBagReminderUiState.Error(it.message ?: "Unknown error")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HolidayBagReminderUiState.Loading
            )

    fun toggleEditMode() {
        isEditing = !isEditing
    }

    fun toggleItemChecked(itemId: String, checked: Boolean) {
        viewModelScope.launch {
            holidayRepository.checkItemInBag(holidayId, itemId, checked)
        }
    }

    fun changeHolidayDuration(duration: Int) {
        viewModelScope.launch {
            holidayRepository.setHolidayDuration(holidayId, duration)
        }
    }

    fun reinitializeHoliday() {
        viewModelScope.launch {
            holidayRepository.reset(holidayId)
        }
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

        val newItem = ItemInBagUiState(
            id = UUID.randomUUID().toString(),
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
    private fun updateItems(updatedItems: List<ItemInBagUiState>) {
        /*
        _uiState.value = _uiState.value.copy(items = updatedItems)
        holidayRepository.saveHolidayData(_uiState.value.toDomainModel())
         */
    }
}


