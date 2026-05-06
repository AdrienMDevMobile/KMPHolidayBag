package m.adrien.kmpholiday.view.holiday.value

import m.adrien.kmpholiday.domain.HolidayReminder

fun HolidayReminder.toUiState(): HolidayReminderUiState {
    return HolidayReminderUiState.Value(
        name = this.name,
        durationDay = this.duration,
        items = this.items.map { item ->
            val quantity = if (item.isDurationDependant) {
                calculateQuantity(item.quantity, this.duration)
            } else {
                item.quantity
            }
            HolidayItemUiState(
                name = item.name,
                checked = item.checked,
                quantity = quantity,
            )
        }.sortedBy { it.checked } // Checked items at bottom
    )
}

private fun calculateQuantity(baseQuantity: Int, durationDays: Int) = baseQuantity * durationDays