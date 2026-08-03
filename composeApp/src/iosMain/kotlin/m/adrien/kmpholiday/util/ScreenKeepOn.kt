package m.adrien.kmpholiday.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import platform.UIKit.UIApplication

/**
 * Composable that keeps the screen on when the given condition is true.
 * 
 * @param keepScreenOn If true, the screen will be kept on; if false, the screen will be allowed to turn off.
 */
@Composable
actual fun KeepScreenOn(keepScreenOn: Boolean) {
    DisposableEffect(keepScreenOn) {
        val application = UIApplication.sharedApplication
        val wasIdleTimerDisabled = application.isIdleTimerDisabled
        
        if (keepScreenOn) {
            application.isIdleTimerDisabled = true
        }
        
        onDispose {
            // Restore the previous state when this composable is disposed
            application.isIdleTimerDisabled = wasIdleTimerDisabled
        }
    }
}
