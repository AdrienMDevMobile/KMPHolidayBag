package m.adrien.kmpholiday.view.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import m.adrien.kmpholiday.view.settings.value.SettingsNavigationEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val events by viewModel.navigationEvents.collectAsState()

    // Handle navigation events as a cold flow
    LaunchedEffect(events) {
        events.firstOrNull()?.let {
            when (it) {
                SettingsNavigationEvent.NavigateBack -> {
                    onNavigateBack()
                    viewModel.onNavigationEventProcessed(it.id)
                }
            }
        }
    }

    SettingsPage(
        uiState = uiState,
        onKeepScreenOnToggle = { viewModel.toggleKeepScreenOn() },
        onInfoButtonClick = { viewModel.toggleShowTooltipKeepScreenOn() },
        onBackPressed = { viewModel.onBackPressed() },
        modifier = modifier
    )
}

@Composable
fun SettingsPage(
    uiState: SettingsUiState,
    onKeepScreenOnToggle: () -> Unit,
    onInfoButtonClick: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Settings Header with Back Button
            SettingsHeader(
                onBackPressed = onBackPressed,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Keep Screen On",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    IconButton(onClick = { onInfoButtonClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Text(
                    text = "Keep screen on while inside the list of a holiday bag",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .alpha(
                            if (uiState.showTooltip) {
                                1f
                            } else {
                                0f
                            }
                        )
                )

                Switch(
                    checked = uiState.keepScreenOn,
                    onCheckedChange = { onKeepScreenOnToggle() }
                )
            }
        }
    }
}

@Composable
fun SettingsHeader(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(onClick = onBackPressed) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPagePreview() {
    MaterialTheme {
        SettingsPage(
            uiState = SettingsUiState(keepScreenOn = true, showTooltip = false),
            onKeepScreenOnToggle = {},
            onInfoButtonClick = {},
            onBackPressed = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPageTooltipPreview() {
    MaterialTheme {
        SettingsPage(
            uiState = SettingsUiState(keepScreenOn = false, showTooltip = true),
            onKeepScreenOnToggle = {},
            onInfoButtonClick = {},
            onBackPressed = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsHeaderPreview() {
    MaterialTheme {
        SettingsHeader(
            onBackPressed = {}
        )
    }
}