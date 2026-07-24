package m.adrien.kmpholiday.view.holidayBag.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import m.adrien.kmpholiday.view.holidayBag.value.ItemInBagUiState

@Composable
fun ItemsInBagList(
    items: List<ItemInBagUiState>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (items.isEmpty()) {
                Text(
                    text = "No items yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        ItemInBag(item = item)
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ItemsInBagListPreview() {
    MaterialTheme {
        ItemsInBagList(
            items = listOf(
                ItemInBagUiState(
                    name = "Swimsuit",
                    checked = true,
                    quantity = 2
                ),
                ItemInBagUiState(
                    name = "Sunglasses",
                    checked = false,
                    quantity = 1
                ),
                ItemInBagUiState(
                    name = "Beach Towel",
                    checked = true,
                    quantity = 3
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemsInBagListEmptyPreview() {
    MaterialTheme {
        ItemsInBagList(
            items = emptyList()
        )
    }
}