package m.adrien.kmpholiday.view.holidays

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HolidayRemindersList(
    list: List<HolidayReminderPreviewUiState>,
    goToHoliday: (String) -> Unit,
) {
    list.forEach { holiday ->
        HolidayReminderButton(
            uiState = HolidayReminderPreviewUiState(
                name = holiday.name,
                id = holiday.id
            ),
            onClick = goToHoliday,
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}


@Preview
@Composable
fun HolidayRemindersList() {
    Column {
        val sampleReminders = listOf(
            HolidayReminderPreviewUiState("Summer Vacation", "summer_2024"),
            HolidayReminderPreviewUiState("Winter Break", "winter_2024"),
            HolidayReminderPreviewUiState("Spring Trip", "spring_2025")
        )
        HolidayRemindersList(
            sampleReminders, {}
        )
    }
}