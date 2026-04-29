package m.adrien.kmpholiday.view.holiday

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HolidayHeader(
    name: String,
    durationDay: Int,
    onDurationChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isEditingDefault: Boolean = false
) {
    var durationText by remember { mutableStateOf(durationDay.toString()) }
    var isEditing by remember { mutableStateOf(isEditingDefault) }
    
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = MaterialTheme.colorScheme.primary
        )
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
                    isEditing = false
                }
            )
        } else {
            HolidayHeaderDisplayMode(
                durationDay = durationDay,
                onEditClick = { isEditing = true }
            )
        }
    }
}

@Composable
fun HolidayHeaderEditMode(
    durationText: String,
    onDurationTextChange: (String) -> Unit,
    onSave: () -> Unit,
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
            )
        )
        Button(onClick = onSave) {
            Text("Save")
        }
    }
}

@Composable
fun HolidayHeaderDisplayMode(
    durationDay: Int,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Duration: $durationDay days",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        IconButton(onClick = onEditClick) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit duration"
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
            onDurationChange = { },
            isEditingDefault = false,
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
            onDurationChange = { },
            isEditingDefault = true,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayHeaderEditModePreview() {
    MaterialTheme {
        HolidayHeaderEditMode(
            durationText = "14",
            onDurationTextChange = {  },
            onSave = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayHeaderDisplayModePreview() {
    MaterialTheme {
        HolidayHeaderDisplayMode(
            durationDay = 14,
            onEditClick = { }
        )
    }
}