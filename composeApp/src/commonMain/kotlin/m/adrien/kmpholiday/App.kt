package m.adrien.kmpholiday

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import m.adrien.kmpholiday.data.HolidayReminderPreviewsRepositoryStaticImpl
import m.adrien.kmpholiday.view.holiday.HolidayScreen
import m.adrien.kmpholiday.view.holidays.HolidaysScreen
import m.adrien.kmpholiday.view.holidays.HolidaysViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Holidays
        ) {
            composable<Holidays> {
                HolidaysScreen(
                    goToHoliday = { id -> navController.navigate(Holiday(id)) },
                    viewModel = HolidaysViewModel(HolidayReminderPreviewsRepositoryStaticImpl())
                )
            }
            composable<Holiday> { id ->
                HolidayScreen()
            }


        }

    }
}

@Serializable
object Holidays

@Serializable
data class Holiday(val id: String)
