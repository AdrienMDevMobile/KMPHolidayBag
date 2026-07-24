package m.adrien.kmpholiday.view.holiday.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import m.adrien.kmpholiday.view.holiday.value.ItemInBagUiState
import m.adrien.kmpholiday.view.holiday.value.HolidayBagReminderUiState
import m.adrien.kmpholiday.view.shared.ErrorPage
import m.adrien.kmpholiday.view.shared.LoadingPage

@Composable
fun HolidayBagReminderScreen(uiState: HolidayBagReminderUiState) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (uiState) {
            HolidayBagReminderUiState.Loading -> {
                LoadingPage()
            }
            is HolidayBagReminderUiState.Value -> {
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

                    ItemsInBagList(items = uiState.items)
                }
            }
            is HolidayBagReminderUiState.Error -> {
                ErrorPage(errorMessage = uiState.message)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayBagReminderScreenPreview() {
    MaterialTheme {
        HolidayBagReminderScreen(
            uiState = HolidayBagReminderUiState.Value(
                name = "Summer Vacation",
                durationDay = 14,
                items = listOf(
                    ItemInBagUiState(
                        name = "Swimsuit",
                        checked = true,
                        quantity = 2
                    ),
                    ItemInBagUiState(
                        name = "Sunglasses",
                        checked = false,
                        quantity = 1
                    ),
                    ItemInBagUiState(
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
fun HolidayBagReminderScreenEmptyPreview() {
    MaterialTheme {
        HolidayBagReminderScreen(
            uiState = HolidayBagReminderUiState.Value(
                name = "Winter Getaway",
                durationDay = 7,
                items = emptyList()
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayBagReminderScreenLoadingPreview() {
    MaterialTheme {
        HolidayBagReminderScreen(
            uiState = HolidayBagReminderUiState.Loading
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayBagReminderScreenErrorPreview() {
    MaterialTheme {
        HolidayBagReminderScreen(
            uiState = HolidayBagReminderUiState.Error("Failed to load holiday data")
        )
    }
}