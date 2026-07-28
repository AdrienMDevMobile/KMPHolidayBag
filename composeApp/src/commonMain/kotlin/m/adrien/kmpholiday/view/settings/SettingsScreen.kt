package m.adrien.kmpholiday.view.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SettingsPage(
        uiState = uiState,
        onKeepScreenOnToggle = { viewModel.toggleKeepScreenOn() },
        onInfoButtonClick = { viewModel.toggleShowTooltipKeepScreenOn() }
    )
}

@Composable
fun SettingsPage(
    uiState: SettingsUiState,
    onKeepScreenOnToggle: () -> Unit,
    onInfoButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Keep Screen On Setting
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Keep Screen On",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Switch and Info Button Row
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = uiState.keepScreenOn,
                        onCheckedChange = { onKeepScreenOnToggle() }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { onInfoButtonClick() },
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text("ℹ️")
                    }
                }

                // Tooltip/Info Text
                if (uiState.showTooltip) {
                    Text(
                        text = "Keep screen on while inside the list of a holiday bag",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPagePreview() {
    MaterialTheme {
        SettingsPage(
            uiState = SettingsUiState(keepScreenOn = true, showTooltip = false),
            onKeepScreenOnToggle = {},
            onInfoButtonClick = {}
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
            onInfoButtonClick = {}
        )
    }
}