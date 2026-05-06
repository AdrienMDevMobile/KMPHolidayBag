package m.adrien.kmpholiday.view.holiday.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import m.adrien.kmpholiday.view.holiday.value.HolidayItemUiState
import m.adrien.kmpholiday.view.holiday.value.HolidayReminderUiState
import m.adrien.kmpholiday.view.shared.ErrorPage
import m.adrien.kmpholiday.view.shared.LoadingPage

@Composable
fun HolidayReminderScreen(uiState: HolidayReminderUiState) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (uiState) {
            HolidayReminderUiState.Loading -> {
                LoadingPage()
            }
            is HolidayReminderUiState.Value -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    HolidayHeader(
                        name = uiState.name,
                        durationDay = uiState.durationDay,
                        onDurationChange = { /* TODO: Handle duration change */ }
                    )

                    HolidayItemsList(items = uiState.items)
                }
            }
            is HolidayReminderUiState.Error -> {
                ErrorPage(errorMessage = uiState.message)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayReminderScreenPreview() {
    MaterialTheme {
        HolidayReminderScreen(
            uiState = HolidayReminderUiState.Value(
                name = "Summer Vacation",
                durationDay = 14,
                items = listOf(
                    HolidayItemUiState(
                        name = "Swimsuit",
                        checked = true,
                        quantity = 2
                    ),
                    HolidayItemUiState(
                        name = "Sunglasses",
                        checked = false,
                        quantity = 1
                    ),
                    HolidayItemUiState(
                        name = "Beach Towel",
                        checked = true,
                        quantity = 3
                    )
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayReminderScreenEmptyPreview() {
    MaterialTheme {
        HolidayReminderScreen(
            uiState = HolidayReminderUiState.Value(
                name = "Winter Getaway",
                durationDay = 7,
                items = emptyList()
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayReminderScreenLoadingPreview() {
    MaterialTheme {
        HolidayReminderScreen(
            uiState = HolidayReminderUiState.Loading
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayReminderScreenErrorPreview() {
    MaterialTheme {
        HolidayReminderScreen(
            uiState = HolidayReminderUiState.Error("Failed to load holiday data")
        )
    }
}