package m.adrien.kmpholiday

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import m.adrien.kmpholiday.di.initKoin

fun main() {
    // Initialize Koin
    initKoin()
    
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KMPHoliday",
        ) {
            App()
        }
    }
}