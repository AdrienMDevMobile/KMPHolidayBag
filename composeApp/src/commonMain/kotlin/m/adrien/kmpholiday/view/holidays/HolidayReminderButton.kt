package m.adrien.kmpholiday.view.holidays

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HolidayReminderButton(
    uiState: HolidayReminderPreviewUiState,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.clickable(
            onClick = {
                onClick(uiState.id)
            }
        )
            .fillMaxWidth()
            .border(BorderStroke(5.dp, Color.Black))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = uiState.name,
                modifier = Modifier.padding(start = 16.dp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "View details",
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}

@Composable
@Preview
fun HolidayReminderButtonPreview() {
    MaterialTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
            HolidayReminderButton(
                uiState = HolidayReminderPreviewUiState(
                    name = "Vacances d'été",
                    id = "summer_2024"
                ),
                onClick = {}
            )
        }
    }
}