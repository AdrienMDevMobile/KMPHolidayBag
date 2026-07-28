package m.adrien.kmpholiday.view.holidays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import m.adrien.kmpholiday.view.holidays.value.HolidaysNavigationEvent
import m.adrien.kmpholiday.view.shared.ErrorPage
import m.adrien.kmpholiday.view.shared.LoadingPage

@Composable
fun HolidaysScreen(
    onNavigateToHoliday: (String) -> Unit,
    viewModel: HolidaysViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val events by viewModel.navigationEvents.collectAsState()

    // Handle navigation events as a cold flow
    LaunchedEffect(events) {
        events.firstOrNull()?.let {
            when (it) {
                is HolidaysNavigationEvent.NavigateToHoliday -> {
                    onNavigateToHoliday(it.holidayId)
                    viewModel.onNavigationEventProcessed(it.id)
                }
            }
        }
    }

    HolidaysPage(state, { holidayId -> viewModel.onHolidayClick(holidayId) })
}

@Composable
fun HolidaysPage(
    uiState: HolidayBagRemindersUiState,
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
            is HolidayBagRemindersUiState.Loading -> {
                LoadingPage()
            }

            is HolidayBagRemindersUiState.Error -> {
                ErrorPage("Error: ${uiState.message}")
            }

            is HolidayBagRemindersUiState.Success -> {
                HolidayBagRemindersList(uiState.reminders, goToHoliday)
            }
        }
    }
}

@Preview
@Composable
fun HolidaysPageSuccessPreview() {
    val sampleReminders = listOf(
        HolidayBagReminderPreviewUiState("Summer Vacation", "summer_2024"),
        HolidayBagReminderPreviewUiState("Winter Break", "winter_2024"),
        HolidayBagReminderPreviewUiState("Spring Trip", "spring_2025")
    )
    HolidaysPage(
        uiState = HolidayBagRemindersUiState.Success(sampleReminders),
        {}
    )
}

@Preview
@Composable
fun HolidaysPageLoadingPreview() {
    HolidaysPage(
        uiState = HolidayBagRemindersUiState.Loading, {}
    )
}

@Preview
@Composable
fun HolidaysPageErrorPreview() {
    HolidaysPage(
        uiState = HolidayBagRemindersUiState.Error("Failed to load holidays"),
        {}
    )
}