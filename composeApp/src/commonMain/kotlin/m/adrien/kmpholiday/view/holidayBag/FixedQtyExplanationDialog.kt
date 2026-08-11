package m.adrien.kmpholiday.view.holidayBag

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kmpholiday.composeapp.generated.resources.Res
import kmpholiday.composeapp.generated.resources.fixed_qty_column_title
import kmpholiday.composeapp.generated.resources.fixed_qty_explanation_text
import kmpholiday.composeapp.generated.resources.ok_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun FixedQtyExplanationDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(Res.string.fixed_qty_column_title)) },
        text = { Text(stringResource(Res.string.fixed_qty_explanation_text)) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.ok_button))
            }
        }
    )
}

@Preview
@Composable
fun FixedQtyExplanationDialogPreview() {
    FixedQtyExplanationDialog(onDismiss = {})
}
