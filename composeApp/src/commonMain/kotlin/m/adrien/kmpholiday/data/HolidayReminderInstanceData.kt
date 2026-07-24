package m.adrien.kmpholiday.data

import kotlinx.serialization.Serializable

@Serializable
data class HolidayReminderInstanceData(
    val id: String,
    val duration: Int,
    val itemChecked: List<ItemInBagId>
)