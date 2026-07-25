package m.adrien.kmpholiday

import androidx.compose.ui.window.ComposeUIViewController
import m.adrien.kmpholiday.di.initKoin

fun MainViewController() = ComposeUIViewController {
    // Initialize Koin for iOS
    initKoin()
    App()
}