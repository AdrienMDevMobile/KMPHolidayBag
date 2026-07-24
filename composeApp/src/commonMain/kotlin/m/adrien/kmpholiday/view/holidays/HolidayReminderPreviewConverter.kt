package m.adrien.kmpholiday.view.holidays

import m.adrien.kmpholiday.domain.HolidayBagReminderPreview

fun HolidayBagReminderPreview.toUi(): HolidayReminderPreviewUiState {
    return HolidayReminderPreviewUiState(
        name = this.name,
        id = this.id
    )
}

fun HolidayReminderPreviewUiState.toDomain(): HolidayBagReminderPreview {
    return HolidayBagReminderPreview(
        name = this.name,
        id = this.id
    )
}