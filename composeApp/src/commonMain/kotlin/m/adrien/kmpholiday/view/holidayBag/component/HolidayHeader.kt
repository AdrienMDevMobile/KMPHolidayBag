package m.adrien.kmpholiday.view.holidayBag.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmpholiday.composeapp.generated.resources.Res
import kmpholiday.composeapp.generated.resources.back_button_content_description
import kmpholiday.composeapp.generated.resources.cancel_button
import kmpholiday.composeapp.generated.resources.duration_label
import kmpholiday.composeapp.generated.resources.edit_duration_content_description
import kmpholiday.composeapp.generated.resources.reinitialize_holiday_content_description
import kmpholiday.composeapp.generated.resources.save_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun HolidayHeader(
    name: String,
    durationDay: Int,
    isEditing: Boolean,
    onEditModeToggle: () -> Unit,
    onDurationChange: (Int) -> Unit,
    onReinitialize: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    var durationText by remember { mutableStateOf(durationDay.toString()) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.back_button_content_description)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (isEditing) {
            HolidayHeaderEditMode(
                durationText = durationText,
                onDurationTextChange = { newText ->
                    durationText = newText
                },
                onSave = {
                    val newDuration = durationText.toIntOrNull() ?: durationDay
                    onDurationChange(newDuration)
                    onEditModeToggle()
                },
                onCancel = {
                    onEditModeToggle()
                }
            )
        } else {
            HolidayHeaderDisplayMode(
                durationDay = durationDay,
                onEditClick = { onEditModeToggle() },
                onReinitialize = { onReinitialize() }
            )
        }
    }
}

@Composable
fun HolidayHeaderEditMode(
    durationText: String,
    onDurationTextChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = durationText,
            onValueChange = onDurationTextChange,
            label = { Text("Duration (days)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.width(150.dp)
        )
        IconButton(onClick = onCancel) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(Res.string.cancel_button)
            )
        }
        IconButton(onClick = onSave) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = stringResource(Res.string.save_button)
            )
        }
    }
}

@Composable
fun HolidayHeaderDisplayMode(
    durationDay: Int,
    onEditClick: () -> Unit,
    onReinitialize: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.duration_label, durationDay),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        IconButton(onClick = onEditClick) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = stringResource(Res.string.edit_duration_content_description)
            )
        }
        IconButton(onClick = onReinitialize) {
            Icon(
                imageVector = Icons.Filled.Replay,
                contentDescription = stringResource(Res.string.reinitialize_holiday_content_description)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayHeaderPreview() {
    MaterialTheme {
        HolidayHeader(
            name = "Summer Vacation",
            durationDay = 14,
            isEditing = false,
            onEditModeToggle = { },
            onDurationChange = { },
            onReinitialize = { },
            onBackPressed = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayHeaderPreviewEditing() {
    MaterialTheme {
        HolidayHeader(
            name = "Summer Vacation",
            durationDay = 14,
            isEditing = true,
            onEditModeToggle = { },
            onDurationChange = { },
            onReinitialize = { },
            onBackPressed = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayHeaderEditModePreview() {
    MaterialTheme {
        HolidayHeaderEditMode(
            durationText = "14",
            onDurationTextChange = { },
            onSave = { },
            onCancel = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayHeaderDisplayModePreview() {
    MaterialTheme {
        HolidayHeaderDisplayMode(
            durationDay = 14,
            onEditClick = { },
            onReinitialize = { }
        )
    }
}