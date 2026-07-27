package m.adrien.kmpholiday.view.holidayBag

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderRepository
import m.adrien.kmpholiday.view.holidayBag.value.HolidayBagReminderUiState
import m.adrien.kmpholiday.view.holidayBag.value.InitializeBagDialogUiState
import m.adrien.kmpholiday.view.holidayBag.value.ItemInBagUiState
import m.adrien.kmpholiday.view.holidayBag.value.toUiState

class HolidayBagReminderViewModel(
    val holidayRepository: HolidayBagReminderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    private val holidayId = savedStateHandle.get<String>("holidayId") ?: ""

    private var isEditing by mutableStateOf(false)

    val initializeBagDialogUiState: StateFlow<InitializeBagDialogUiState?>
        get() = _initializeBagDialogUiState
    private val _initializeBagDialogUiState: MutableStateFlow<InitializeBagDialogUiState?> =
        MutableStateFlow(null)

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

    fun reinitializeHolidayWithDuration() {
        viewModelScope.launch {
            val currentState = _initializeBagDialogUiState.value ?: return@launch
            val durationString = currentState.duration.ifBlank { currentState.durationDefault.toString() }
            val error = getDurationGetErrorCode(durationString)
            if(error == null){
                
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
        if(duration.isBlank()) {
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


