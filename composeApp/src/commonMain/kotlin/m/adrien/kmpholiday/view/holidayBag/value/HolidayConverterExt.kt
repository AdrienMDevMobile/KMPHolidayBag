package m.adrien.kmpholiday.view.holidayBag.value

import m.adrien.kmpholiday.domain.HolidayBagReminder

fun HolidayBagReminder.toUiState(): HolidayBagReminderUiState {
    return HolidayBagReminderUiState.Value(
        name = this.name,
        durationDay = this.duration,
        items = this.items.map { item ->
            val quantity = if (item.isDurationDependant) {
                calculateQuantity(item.quantity, this.duration)
            } else {
                item.quantity
            }
            ItemInBagUiState(
                id = item.id,
                name = item.name,
                checked = item.checked,
                quantity = quantity,
            )
        }.sortedBy { it.checked } // Checked items at bottom
    )
}

private fun calculateQuantity(baseQuantity: Int, durationDays: Int) = baseQuantity * durationDays