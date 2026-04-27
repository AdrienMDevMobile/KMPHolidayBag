package m.adrien.kmpholiday

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import m.adrien.kmpholiday.data.HolidayReminderPreviewsRepositoryStaticImpl
import m.adrien.kmpholiday.view.holiday.HolidayScreen
import m.adrien.kmpholiday.view.holidays.HolidaysScreen
import m.adrien.kmpholiday.view.holidays.HolidaysViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Holidays) }

        when (currentScreen) {
            Screen.Holidays -> HolidaysScreen(
                goToHoliday = { currentScreen = Screen.Holiday },
                viewModel = HolidaysViewModel(HolidayReminderPreviewsRepositoryStaticImpl())
            )

            Screen.Holiday -> HolidayScreen()
        }
    }
}

enum class Screen {
    Holidays,
    Holiday
}