package m.adrien.kmpholiday.view.holidayBag.value

import m.adrien.kmpholiday.domain.HolidayBagReminder

fun HolidayBagReminder.toUiState(isEditingOn: Boolean): HolidayBagReminderUiState {
    val items = if (isEditingOn) {
        this.items.map { item ->
            ItemInBagUiState.EditMode(
                id = item.id,
                name = item.name,
                quantity = item.quantity,
                isDurationIndependant = item.isDurationIndependant,
            )
        }
    } else {
        this.items.map { item ->
            val quantity = if (item.isDurationIndependant) {
                item.quantity
            } else {
                calculateQuantity(item.quantity, this.duration)
            }
            ItemInBagUiState.CheckMode(
                id = item.id,
                name = item.name,
                checked = item.checked,
                quantity = quantity,
            )
        }.sortedBy { it.checked } // Checked items at bottom TODO also apply the check to the edit version ?
    }

    return HolidayBagReminderUiState.Value(
        name = this.name,
        durationDay = this.duration,
        items = items,
        isEditingOn = isEditingOn,
        isComplete = this.items.isNotEmpty() && this.items.all { it.checked }
    )
}

private fun calculateQuantity(baseQuantity: Int, durationDays: Int) = baseQuantity * durationDays
