package m.adrien.kmpholiday

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

import m.adrien.kmpholiday.view.holiday.HolidayScreen
import m.adrien.kmpholiday.view.holidays.HolidaysScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Holidays) }
        
        when (currentScreen) {
            Screen.Holidays -> HolidaysScreen(
                goToHoliday = { currentScreen = Screen.Holiday }
            )
            Screen.Holiday -> HolidayScreen()
        }
    }
}

enum class Screen {
    Holidays,
    Holiday
}