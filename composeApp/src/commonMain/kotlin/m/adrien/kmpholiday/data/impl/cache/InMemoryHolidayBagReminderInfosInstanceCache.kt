package m.adrien.kmpholiday.data.impl.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import m.adrien.kmpholiday.data.HolidayReminderInstanceData

//TODO PlaceHolder, version others than Android don't use real cache
class InMemoryHolidayBagReminderInfosInstanceCache : HolidayBagReminderInfosInstanceCache {
    private val reminderDataMap = mutableMapOf<String, MutableStateFlow<HolidayReminderInstanceData?>>()
    
    override fun getReminderInstance(holidayId: String): Flow<HolidayReminderInstanceData?> {
        return reminderDataMap.getOrPut(holidayId) {
            MutableStateFlow(null)
        }.asStateFlow()
    }
    
    override suspend fun saveReminderInstance(holidayId: String, reminderData: HolidayReminderInstanceData) {
        reminderDataMap.getOrPut(holidayId) {
            MutableStateFlow(null)
        }.update { reminderData }
    }

    override suspend fun setReminderInstanceDuration(holidayId: String, duration: Int) {
        val currentFlow = reminderDataMap.getOrPut(holidayId) {
            MutableStateFlow(null)
        }
        val currentData = currentFlow.value ?: HolidayReminderInstanceData(holidayId, 0, emptyList())
        val updatedData = currentData.copy(duration = duration)
        currentFlow.update { updatedData }
    }

    override suspend fun checkItem(
        holidayId: String,
        itemId: String,
        checked: Boolean
    ) {
        val currentFlow = reminderDataMap[holidayId] ?: return
        val currentData = currentFlow.value ?: HolidayReminderInstanceData(holidayId, 0, emptyList())
        
        val updatedCheckedItems = if (checked) {
            // Add item to checked list if not already present
            if (itemId !in currentData.itemChecked) {
                currentData.itemChecked + itemId
            } else {
                currentData.itemChecked
            }
        } else {
            // Remove item from checked list if present
            currentData.itemChecked.filterNot { it == itemId }
        }
        
        val updatedData = currentData.copy(itemChecked = updatedCheckedItems)
        currentFlow.update { updatedData }
    }

    override suspend fun emptyHolidayBagWithNewDuration(holidayId: String, duration: Int) {
        // Set new duration for the holiday
        val currentFlow = reminderDataMap.getOrPut(holidayId) {
            MutableStateFlow(null)
        }
        val currentData = currentFlow.value ?: HolidayReminderInstanceData(holidayId, 0, emptyList())
        val updatedData = currentData.copy(duration = duration)
        currentFlow.update { updatedData }
    }
}