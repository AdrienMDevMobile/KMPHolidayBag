package m.adrien.kmpholiday.view.holiday

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HolidayReminderScreen(uiState: HolidayReminderUiState) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
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
}

@Preview(showBackground = true)
@Composable
fun HolidayReminderScreenPreview() {
    MaterialTheme {
        HolidayReminderScreen(
            uiState = HolidayReminderUiState(
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
            uiState = HolidayReminderUiState(
                name = "Winter Getaway",
                durationDay = 7,
                items = emptyList()
            )
        )
    }
}