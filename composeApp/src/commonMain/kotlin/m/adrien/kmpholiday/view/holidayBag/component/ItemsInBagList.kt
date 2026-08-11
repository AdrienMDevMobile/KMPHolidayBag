package m.adrien.kmpholiday.view.holidayBag.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kmpholiday.composeapp.generated.resources.Res
import kmpholiday.composeapp.generated.resources.fixed_qty_column_title
import kmpholiday.composeapp.generated.resources.fixed_qty_info_content_description
import kmpholiday.composeapp.generated.resources.items_list_empty
import kmpholiday.composeapp.generated.resources.quantity_column_title
import org.jetbrains.compose.resources.stringResource
import m.adrien.kmpholiday.view.holidayBag.value.ItemInBagUiState

@Composable
fun ItemsInBagList(
    items: List<ItemInBagUiState>,
    isEditingOn: Boolean,
    onItemCheckedChange: (String, Boolean) -> Unit = { _, _ -> },
    onItemQuantityChange: (String, Int) -> Unit = { _, _ -> },
    onItemDurationIndependantChange: (String, Boolean) -> Unit = { _, _ -> },
    onItemDelete: (String) -> Unit = {},
    onFixedQtyInfoClick: () -> Unit = {},
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
                    text = stringResource(Res.string.items_list_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                ItemsInBagListHeader(
                    isEditingOn = isEditingOn,
                    onFixedQtyInfoClick = onFixedQtyInfoClick
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items, key = { it.id }) { item ->
                        ItemInBag(
                            item = item,
                            onCheckedChange = { checked -> onItemCheckedChange(item.id, checked) },
                            onQuantityChange = { quantity ->
                                onItemQuantityChange(
                                    item.id,
                                    quantity
                                )
                            },
                            onDurationIndependantChange = { durationIndependant ->
                                onItemDurationIndependantChange(item.id, durationIndependant)
                            },
                            onDelete = { onItemDelete(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemsInBagListHeader(
    isEditingOn: Boolean,
    onFixedQtyInfoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        if (isEditingOn) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(Res.string.quantity_column_title),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(QUANTITY_STEPPER_COLUMN_WIDTH)
                )
                Row(
                    modifier = Modifier.width(FIXED_QTY_COLUMN_WIDTH),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(Res.string.fixed_qty_column_title),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = onFixedQtyInfoClick,
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                            contentDescription = stringResource(Res.string.fixed_qty_info_content_description),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(DELETE_COLUMN_WIDTH))
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(Res.string.quantity_column_title),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(QUANTITY_COLUMN_WIDTH)
                )
                Spacer(modifier = Modifier.width(CHECKBOX_COLUMN_WIDTH))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemsInBagListPreview() {
    MaterialTheme {
        ItemsInBagList(
            isEditingOn = false,
            items = listOf(
                ItemInBagUiState.CheckMode(
                    id = "swimsuit",
                    name = "Swimsuit",
                    checked = true,
                    quantity = 2
                ),
                ItemInBagUiState.CheckMode(
                    id = "sunglasses",
                    name = "Sunglasses",
                    checked = false,
                    quantity = 1
                ),
                ItemInBagUiState.CheckMode(
                    id = "beach_towel",
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
fun ItemsInBagListEditingPreview() {
    MaterialTheme {
        ItemsInBagList(
            isEditingOn = true,
            items = listOf(
                ItemInBagUiState.EditMode(
                    id = "swimsuit",
                    name = "Swimsuit",
                    quantity = 2,
                    isDurationIndependant = true
                ),
                ItemInBagUiState.EditMode(
                    id = "sunglasses",
                    name = "Sunglasses",
                    quantity = 1,
                    isDurationIndependant = true
                ),
                ItemInBagUiState.EditMode(
                    id = "beach_towel",
                    name = "Beach Towel",
                    quantity = 1,
                    isDurationIndependant = false
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
            isEditingOn = false,
            items = emptyList()
        )
    }
}
