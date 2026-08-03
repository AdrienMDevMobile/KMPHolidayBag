package m.adrien.kmpholiday.util

import androidx.compose.runtime.Composable

/**
 * Composable that keeps the screen on when the given condition is true.
 * For JVM/desktop, this is a no-op as screen keep-on is not typically supported.
 * 
 * @param keepScreenOn If true, the screen would be kept on; if false, the screen would be allowed to turn off.
 */
@Composable
actual fun KeepScreenOn(keepScreenOn: Boolean) {
    // No-op for JVM/desktop - screen keep-on is not typically supported
}
