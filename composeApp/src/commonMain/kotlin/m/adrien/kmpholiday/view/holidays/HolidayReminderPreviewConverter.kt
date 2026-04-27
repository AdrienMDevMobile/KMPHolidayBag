package m.adrien.kmpholiday.view.holidays

import m.adrien.kmpholiday.domain.HolidayReminderPreview

fun HolidayReminderPreview.toUi(): HolidayReminderPreviewUiState {
    return HolidayReminderPreviewUiState(
        name = this.name,
        id = this.id
    )
}

fun HolidayReminderPreviewUiState.toDomain(): HolidayReminderPreview {
    return HolidayReminderPreview(
        name = this.name,
        id = this.id
    )
}