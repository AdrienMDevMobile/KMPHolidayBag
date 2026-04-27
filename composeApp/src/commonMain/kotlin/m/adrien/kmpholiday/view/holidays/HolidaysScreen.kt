package m.adrien.kmpholiday.view.holidays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HolidaysScreen(
    goToHoliday: (String) -> Unit,
    viewModel: HolidaysViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    HolidaysPage(state, goToHoliday)
}

@Composable
fun HolidaysPage(
    uiState: HolidayRemindersUiState,
    goToHoliday: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (uiState) {
            is HolidayRemindersUiState.Loading -> {
                CircularProgressIndicator()
            }

            is HolidayRemindersUiState.Error -> {
                Text("Error: ${uiState.message}", color = MaterialTheme.colorScheme.error)
            }

            is HolidayRemindersUiState.Success -> {
                HolidayRemindersList(uiState.reminders, goToHoliday)
            }
        }
    }
}

@Preview
@Composable
fun HolidaysPagePreview() {
    HolidaysPage(
        uiState = HolidayRemindersUiState.Loading, {}
    )
}

@Preview
@Composable
fun HolidaysPageErrorPreview() {
    HolidaysPage(
        uiState = HolidayRemindersUiState.Error("Failed to load holidays"),
        {}
    )
}

@Preview
@Composable
fun HolidaysPageSuccessPreview() {
    val sampleReminders = listOf(
        HolidayReminderPreviewUiState("Summer Vacation", "summer_2024"),
        HolidayReminderPreviewUiState("Winter Break", "winter_2024"),
        HolidayReminderPreviewUiState("Spring Trip", "spring_2025")
    )
    HolidaysPage(
        uiState = HolidayRemindersUiState.Success(sampleReminders),
        {}
    )
}