package m.adrien.kmpholiday.view.holidayBag.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.unit.dp
import kmpholiday.composeapp.generated.resources.Res
import kmpholiday.composeapp.generated.resources.decrease_quantity_content_description
import kmpholiday.composeapp.generated.resources.delete_item_content_description
import kmpholiday.composeapp.generated.resources.increase_quantity_content_description
import m.adrien.kmpholiday.view.holidayBag.value.ItemInBagUiState

// Shared with ItemsInBagListHeader so the column titles line up with each row's controls.
internal val QUANTITY_STEPPER_COLUMN_WIDTH = 76.dp
internal val QUANTITY_STEPPER_NUMBER_WIDTH = 20.dp
internal val FIXED_QTY_COLUMN_WIDTH = 64.dp
internal val DELETE_COLUMN_WIDTH = 32.dp
internal val QUANTITY_COLUMN_WIDTH = 64.dp
internal val CHECKBOX_COLUMN_WIDTH = 48.dp
private val STEPPER_ICON_SIZE = 20.dp

@Composable
fun ItemInBag(
    item: ItemInBagUiState,
    onCheckedChange: (Boolean) -> Unit = {},
    onQuantityChange: (Int) -> Unit = {},
    onDurationIndependantChange: (Boolean) -> Unit = {},
    onDelete: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        when (item) {
            is ItemInBagUiState.EditMode -> {
                ItemInBagEditOptions(
                    item,
                    onQuantityChange,
                    onDurationIndependantChange,
                    onDelete,
                    modifier = Modifier.weight(1f)
                )
            }

            is ItemInBagUiState.CheckMode -> {
                ItemInBagCheckOptions(
                    item,
                    onCheckedChange,
                )
            }
        }
    }
}

@Composable
private fun ItemInBagEditOptions(
    item: ItemInBagUiState.EditMode,
    onQuantityChange: (Int) -> Unit = {},
    onDurationIndependantChange: (Boolean) -> Unit = {},
    onDelete: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.width(QUANTITY_STEPPER_COLUMN_WIDTH),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = stringResource(Res.string.decrease_quantity_content_description),
                modifier = Modifier
                    .size(STEPPER_ICON_SIZE)
                    .clickable { onQuantityChange((item.quantity - 1).coerceAtLeast(0)) }
            )
            Text(
                text = item.quantity.toString(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(QUANTITY_STEPPER_NUMBER_WIDTH)
            )
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(Res.string.increase_quantity_content_description),
                modifier = Modifier
                    .size(STEPPER_ICON_SIZE)
                    .clickable { onQuantityChange(item.quantity + 1) }
            )
        }
        Box(
            modifier = Modifier.width(FIXED_QTY_COLUMN_WIDTH),
            contentAlignment = Alignment.Center
        ) {
            Switch(
                checked = item.isDurationIndependant,
                onCheckedChange = onDurationIndependantChange,
            )
        }
        Box(
            modifier = Modifier.width(DELETE_COLUMN_WIDTH),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(Res.string.delete_item_content_description),
                modifier = Modifier
                    .size(STEPPER_ICON_SIZE)
                    .clickable(onClick = onDelete)
            )
        }
    }
}

@Composable
private fun ItemInBagCheckOptions(
    item: ItemInBagUiState.CheckMode,
    onCheckedChange: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Text(
            text = item.quantity.toString(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(QUANTITY_COLUMN_WIDTH)
        )

        Box(
            modifier = Modifier.width(CHECKBOX_COLUMN_WIDTH),
            contentAlignment = Alignment.Center
        ) {
            Checkbox(
                checked = item.checked,
                onCheckedChange = { checked -> onCheckedChange(checked) },
            )
        }
    }
}

@Preview
@Composable
fun ItemInBagCheckPreview() {
    MaterialTheme {
        Column(modifier = Modifier.background(Color.White)) {
            ItemInBagCheckOptions(
                ItemInBagUiState.CheckMode(
                    id = "item1",
                    name = "Name",
                    checked = true,
                    quantity = 5
                ), {}
            )
        }
    }
}

@Preview
@Composable
fun ItemInBagEditOptionsPreview() {
    MaterialTheme {
        Column(modifier = Modifier.background(Color.White)) {
            ItemInBagEditOptions(
                ItemInBagUiState.EditMode(
                    id = "item2",
                    name = "Name",
                    quantity = 2,
                    isDurationIndependant = true
                ), {}, {}, {}
            )
        }
    }
}


@Preview
@Composable
fun ItemInBagPreviewForCheck() {
    MaterialTheme {
        Column(modifier = Modifier.background(Color.White)) {
            ItemInBag(
                ItemInBagUiState.CheckMode(
                    id = "item1",
                    name = "Name",
                    checked = true,
                    quantity = 5
                )
            )
            ItemInBag(
                ItemInBagUiState.CheckMode(
                    id = "item2",
                    name = "Name",
                    checked = false,
                    quantity = 10
                )
            )
        }
    }
}

@Preview
@Composable
fun ItemInBagPreviewForEditing() {
    MaterialTheme {
        Column(modifier = Modifier.background(Color.White)) {
            ItemInBag(
                ItemInBagUiState.EditMode(
                    id = "item1",
                    name = "Name",
                    quantity = 5,
                    isDurationIndependant = false
                )
            )
            ItemInBag(
                ItemInBagUiState.EditMode(
                    id = "item2",
                    name = "Name",
                    quantity = 2,
                    isDurationIndependant = true
                )
            )
        }
    }
}
