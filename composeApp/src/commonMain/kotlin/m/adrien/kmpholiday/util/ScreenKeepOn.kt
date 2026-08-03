package m.adrien.kmpholiday.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * Composable that keeps the screen on when the given condition is true.
 * 
 * @param keepScreenOn If true, the screen will be kept on; if false, the screen will be allowed to turn off.
 */
@Composable
expect fun KeepScreenOn(keepScreenOn: Boolean)
