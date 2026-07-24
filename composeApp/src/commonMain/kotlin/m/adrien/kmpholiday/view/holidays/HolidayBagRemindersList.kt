package m.adrien.kmpholiday.view.holidays

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HolidayBagRemindersList(
    list: List<HolidayBagReminderPreviewUiState>,
    goToHoliday: (String) -> Unit,
) {
    list.forEach { holiday ->
        HolidayBagReminderButton(
            uiState = HolidayBagReminderPreviewUiState(
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
fun HolidayBagRemindersList() {
    Column {
        val sampleReminders = listOf(
            HolidayBagReminderPreviewUiState("Summer Vacation", "summer_2024"),
            HolidayBagReminderPreviewUiState("Winter Break", "winter_2024"),
            HolidayBagReminderPreviewUiState("Spring Trip", "spring_2025")
        )
        HolidayBagRemindersList(
            sampleReminders, {}
        )
    }
}