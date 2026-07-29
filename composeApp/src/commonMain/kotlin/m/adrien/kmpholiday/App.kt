package m.adrien.kmpholiday

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import m.adrien.kmpholiday.view.holidayBag.HolidayBagReminderScreen
import m.adrien.kmpholiday.view.holidays.HolidaysScreen
import m.adrien.kmpholiday.view.holidays.HolidaysViewModel
import m.adrien.kmpholiday.view.settings.SettingsScreen
import org.koin.compose.viewmodel.koinViewModel

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
                val viewModel: HolidaysViewModel = koinViewModel()
                HolidaysScreen(
                    onNavigateToHoliday = { id -> navController.navigate(Holiday(id)) },
                    onNavigateToSettings = { navController.navigate(Settings) },
                    viewModel = viewModel
                )
            }
            composable<Holiday> {
                HolidayBagReminderScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<Settings> {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Serializable
object Holidays

@Serializable
data class Holiday(val holidayId: String)

@Serializable
object Settings
