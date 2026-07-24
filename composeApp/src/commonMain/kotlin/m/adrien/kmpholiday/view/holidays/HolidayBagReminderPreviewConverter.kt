package m.adrien.kmpholiday.view.holidays

import m.adrien.kmpholiday.domain.HolidayBagReminderPreview

fun HolidayBagReminderPreview.toUi(): HolidayBagReminderPreviewUiState {
    return HolidayBagReminderPreviewUiState(
        name = this.name,
        id = this.id
    )
}

fun HolidayBagReminderPreviewUiState.toDomain(): HolidayBagReminderPreview {
    return HolidayBagReminderPreview(
        name = this.name,
        id = this.id
    )
}