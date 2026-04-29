package m.adrien.kmpholiday.view.holiday

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HolidayItem(
    item: HolidayItemUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Qty: ${item.quantity}",
                style = MaterialTheme.typography.bodyMedium
            )

            Checkbox(
                checked = item.checked,
                onCheckedChange = { },
            )
        }
    }
}

@Preview
@Composable
fun HolidayItemPreview() {
    MaterialTheme {
        Column(modifier = Modifier.background(Color.White)) {
            HolidayItem(HolidayItemUiState("Name", true, 5))
            HolidayItem(HolidayItemUiState("Name", false, 10))
        }
    }
}