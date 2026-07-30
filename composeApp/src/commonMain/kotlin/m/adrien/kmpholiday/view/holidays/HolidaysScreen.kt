package m.adrien.kmpholiday.view.holidays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import m.adrien.kmpholiday.view.holidays.value.HolidaysNavigationEvent
import m.adrien.kmpholiday.view.shared.ErrorPage
import m.adrien.kmpholiday.view.shared.LoadingPage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HolidaysScreen(
    onNavigateToHoliday: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: HolidaysViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
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

                is HolidaysNavigationEvent.NavigateToSettings -> {
                    onNavigateToSettings()
                    viewModel.onNavigationEventProcessed(it.id)
                }
            }
        }
    }

    HolidaysPage(
        state,
        { holidayId ->
            viewModel.onHolidayClick(holidayId)
        },
        {
            viewModel.onSettingsClick()
        },
        modifier = modifier,
    )
}

@Composable
fun HolidaysPage(
    uiState: HolidayBagRemindersUiState,
    goToHoliday: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.background(
            MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Box(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize()
        ) {
            when (uiState) {
                is HolidayBagRemindersUiState.Loading -> {
                    LoadingPage(modifier = Modifier)
                }

                is HolidayBagRemindersUiState.Error -> {
                    ErrorPage(errorMessage = "Error: ${uiState.message}")
                }

                is HolidayBagRemindersUiState.Success -> {
                    HolidaysReminderPageLoaded(uiState.reminders, goToHoliday)
                }
            }
            // Settings button positioned in top-end (top-right)
            IconButton(
                onClick = onNavigateToSettings,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
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
        {}, {}
    )
}

@Preview
@Composable
fun HolidaysPageLoadingPreview() {
    HolidaysPage(
        uiState = HolidayBagRemindersUiState.Loading, {}, {}
    )
}

@Preview
@Composable
fun HolidaysPageErrorPreview() {
    HolidaysPage(
        uiState = HolidayBagRemindersUiState.Error("Failed to load holidays"), {}, {}
    )
}