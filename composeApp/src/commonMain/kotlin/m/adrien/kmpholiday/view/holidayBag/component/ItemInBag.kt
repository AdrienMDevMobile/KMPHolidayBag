package m.adrien.kmpholiday.view.holidayBag.component

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
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.unit.dp
import kmpholiday.composeapp.generated.resources.Res
import kmpholiday.composeapp.generated.resources.quantity_label
import m.adrien.kmpholiday.view.holidayBag.value.ItemInBagUiState

@Composable
fun ItemInBag(
    item: ItemInBagUiState,
    onCheckedChange: (Boolean) -> Unit = {},
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
                text = stringResource(Res.string.quantity_label, item.quantity),
                style = MaterialTheme.typography.bodyMedium
            )

            Checkbox(
                checked = item.checked,
                onCheckedChange = { checked -> onCheckedChange(checked) },
            )
        }
    }
}

@Preview
@Composable
fun ItemInBagPreview() {
    MaterialTheme {
        Column(modifier = Modifier.background(Color.White)) {
            ItemInBag(ItemInBagUiState("item1", "Name", true, 5))
            ItemInBag(ItemInBagUiState("item2", "Name", false, 10))
        }
    }
}