package m.adrien.kmpholiday.view.holidayBag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kmpholiday.composeapp.generated.resources.Res
import kmpholiday.composeapp.generated.resources.cancel_button
import kmpholiday.composeapp.generated.resources.new_duration_label
import kmpholiday.composeapp.generated.resources.validate_button
import m.adrien.kmpholiday.view.holidayBag.value.InitializeBagDialogUiState

@Composable
fun InitializeDialog(
    uiState: InitializeBagDialogUiState,
    onDurationChanged: (String) -> Unit,
    onValidate: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,

) {
    val focusRequester = remember { FocusRequester() }
    
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(onDismissRequest = onCancel) {
        Surface(
            modifier = Modifier
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = uiState.duration ?: "",
                    onValueChange = onDurationChanged,
                    label = { Text(stringResource(Res.string.new_duration_label)) },
                    placeholder = {
                        //if (uiState.duration.isNullOrBlank()) {
                            Text("${uiState.durationDefault}")
                        //}
                    },
                    isError = uiState.error != null,
                    supportingText = {
                        if (uiState.error != null) {
                            Text(uiState.error)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    modifier = Modifier.focusRequester(focusRequester)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(onClick = onCancel) {
                        Text(stringResource(Res.string.cancel_button))
                    }

                    Button(
                        onClick = {
                            onValidate()
                        },
                        enabled = uiState.error == null
                    ) {
                        Text(stringResource(Res.string.validate_button))
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun InitializeDialogPreview() {
    InitializeDialog(
        uiState = InitializeBagDialogUiState(
            durationDefault = 7,
            duration = "7",
            error = null
        ),
        onValidate = {},
        onCancel = {},
        onDurationChanged = {}
    )
}

@Preview
@Composable
fun InitializeDialogErrorPreview() {
    InitializeDialog(
        uiState = InitializeBagDialogUiState(
            durationDefault = 7,
            duration = "-1",
            error = "Duration cannot be negative"
        ),
        onValidate = {},
        onCancel = {},
        onDurationChanged = {}
    )
}

@Preview
@Composable
fun InitializeDialogWithDefaultPreview() {
    InitializeDialog(
        uiState = InitializeBagDialogUiState(
            durationDefault = 14,
            duration = "", // Empty to show default as placeholder
            error = null
        ),
        onValidate = {},
        onCancel = {},
        onDurationChanged = {}
    )
}
