package m.adrien.kmpholiday

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import kmpholiday.composeapp.generated.resources.Res
import kmpholiday.composeapp.generated.resources.compose_multiplatform
import m.adrien.kmpholiday.holiday.HolidayScreen
import m.adrien.kmpholiday.holidays.HolidaysScreen

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