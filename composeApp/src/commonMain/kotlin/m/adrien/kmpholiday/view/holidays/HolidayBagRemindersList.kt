package m.adrien.kmpholiday.view.holidays

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HolidaysReminderPageLoaded(
    list: List<HolidayBagReminderPreviewUiState>,
    goToHoliday: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Holiday Bag Reminders",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 24.dp)
            )
        }

        HolidayBagRemindersList(list, goToHoliday)
    }
}

@Composable
fun HolidayBagRemindersList(
    list: List<HolidayBagReminderPreviewUiState>,
    goToHoliday: (String) -> Unit,
    modifier: Modifier = Modifier,
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
fun HolidayBagRemindersPageLoaded() {
    val sampleReminders = listOf(
        HolidayBagReminderPreviewUiState("Summer Vacation", "summer_2024"),
        HolidayBagReminderPreviewUiState("Winter Break", "winter_2024"),
        HolidayBagReminderPreviewUiState("Spring Trip", "spring_2025")
    )
    HolidaysReminderPageLoaded(
        sampleReminders, {}
    )
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