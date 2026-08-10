package m.adrien.kmpholiday.view.holidayBag.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import kmpholiday.composeapp.generated.resources.Res
import kmpholiday.composeapp.generated.resources.bag_complete_title
import kmpholiday.composeapp.generated.resources.reinitialize_holiday_content_description
import org.jetbrains.compose.resources.stringResource

@Composable
fun BagCompleteHeader(
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(Res.string.bag_complete_title),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(onClick = onReset) {
            Icon(
                imageVector = Icons.Filled.Replay,
                contentDescription = stringResource(Res.string.reinitialize_holiday_content_description)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BagCompleteHeaderPreview() {
    MaterialTheme {
        BagCompleteHeader(onReset = { })
    }
}
