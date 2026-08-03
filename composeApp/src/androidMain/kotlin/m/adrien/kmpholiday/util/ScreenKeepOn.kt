package m.adrien.kmpholiday.util

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView

/**
 * Composable that keeps the screen on when the given condition is true.
 * 
 * @param keepScreenOn If true, the screen will be kept on; if false, the screen will be allowed to turn off.
 */
@Composable
actual fun KeepScreenOn(keepScreenOn: Boolean) {
    val view = LocalView.current
    DisposableEffect(keepScreenOn) {
        view.keepScreenOn = keepScreenOn
        onDispose {
            view.keepScreenOn = false
        }
    }
}
