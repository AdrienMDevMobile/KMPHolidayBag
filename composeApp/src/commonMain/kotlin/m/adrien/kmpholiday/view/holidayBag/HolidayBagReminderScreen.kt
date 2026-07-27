package m.adrien.kmpholiday.view.holidayBag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import m.adrien.kmpholiday.view.holidayBag.InitializeDialog
import m.adrien.kmpholiday.view.holidayBag.component.HolidayHeader
import m.adrien.kmpholiday.view.holidayBag.component.ItemsInBagList
import m.adrien.kmpholiday.view.holidayBag.value.HolidayBagReminderUiState
import m.adrien.kmpholiday.view.holidayBag.value.InitializeBagDialogUiState
import m.adrien.kmpholiday.view.holidayBag.value.ItemInBagUiState
import m.adrien.kmpholiday.view.shared.ErrorPage
import m.adrien.kmpholiday.view.shared.LoadingPage

@Composable
fun HolidayBagReminderScreen(
    viewModel: HolidayBagReminderViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Get current duration for the dialog
    val currentDuration = when (val state = uiState) {
        is HolidayBagReminderUiState.Value -> state.durationDay
        else -> 0
    }
    
    // Get the initialize dialog state from view model
    val initializeDialogUiState by viewModel.initializeBagDialogUiState.collectAsState(null)
    
    HolidayBagReminderPage(
        uiState = uiState,
        initializeBagDialogUiState = initializeDialogUiState,
        onEditModeToggle = { viewModel.toggleEditMode() },
        onItemCheckedChange = { itemId, checked -> viewModel.toggleItemChecked(itemId, checked) },
        onDurationChange = { duration -> viewModel.changeHolidayDuration(duration) },
        onReinitialize = {
            viewModel.showInitializeDialog(currentDuration)
        },
        onValidateReinitialize = {
            viewModel.reinitializeHolidayWithDuration()
        },
        onCancelReinitialize = {
            viewModel.hideInitializeDialog()
        },
        onReinitializeDurationChange = { newDuration ->
            viewModel.updateInitializeDialogDuration(newDuration)
        },
    )
}

@Composable
fun HolidayBagReminderPage(
    uiState: HolidayBagReminderUiState,
    initializeBagDialogUiState: InitializeBagDialogUiState?,
    onEditModeToggle: () -> Unit,
    onItemCheckedChange: (String, Boolean) -> Unit,
    onDurationChange: (Int) -> Unit,
    onReinitialize: () -> Unit,
    onValidateReinitialize: () -> Unit,
    onCancelReinitialize: () -> Unit,
    onReinitializeDurationChange: (String) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (uiState) {
            HolidayBagReminderUiState.Loading -> {
                LoadingPage()
            }

            is HolidayBagReminderUiState.Value -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    HolidayHeader(
                        name = uiState.name,
                        durationDay = uiState.durationDay,
                        isEditing = uiState.isEditing,
                        onEditModeToggle = onEditModeToggle,
                        onDurationChange = onDurationChange,
                        onReinitialize = onReinitialize,
                    )

                    ItemsInBagList(
                        items = uiState.items,
                        onItemCheckedChange = onItemCheckedChange
                    )
                }
            }

            is HolidayBagReminderUiState.Error -> {
                ErrorPage(errorMessage = uiState.message)
            }
        }

        // Show initialize dialog when the state is not null (controlled by view model)
        initializeBagDialogUiState?.let { dialogUiState ->
            InitializeDialog(
                uiState = dialogUiState,
                onValidate = onValidateReinitialize,
                onCancel = onCancelReinitialize,
                onDurationChanged = onReinitializeDurationChange,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayBagReminderScreenPreview() {
    MaterialTheme {
        HolidayBagReminderPage(
            uiState = HolidayBagReminderUiState.Value(
                name = "Summer Vacation",
                durationDay = 14,
                items = listOf(
                    ItemInBagUiState(
                        id = "swimsuit",
                        name = "Swimsuit",
                        checked = true,
                        quantity = 2
                    ),
                    ItemInBagUiState(
                        id = "sunglasses",
                        name = "Sunglasses",
                        checked = false,
                        quantity = 1
                    ),
                    ItemInBagUiState(
                        id = "beach_towel",
                        name = "Beach Towel",
                        checked = true,
                        quantity = 3
                    )
                ),
                isEditing = false
            ),
            onEditModeToggle = { },
            onItemCheckedChange = { string: String, bool: Boolean -> },
            onDurationChange = { },
            onReinitialize = { },
            initializeBagDialogUiState = null,
            onValidateReinitialize = { },
            onCancelReinitialize = { },
            onReinitializeDurationChange = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayBagReminderScreenEmptyPreview() {
    MaterialTheme {
        HolidayBagReminderPage(
            uiState = HolidayBagReminderUiState.Value(
                name = "Winter Getaway",
                durationDay = 7,
                items = emptyList(),
                isEditing = false
            ),
            onEditModeToggle = { },
            onItemCheckedChange = { string: String, bool: Boolean -> },
            onDurationChange = { },
            onReinitialize = { },
            initializeBagDialogUiState = null,
            onValidateReinitialize = { },
            onCancelReinitialize = { },
            onReinitializeDurationChange = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayBagReminderScreenLoadingPreview() {
    MaterialTheme {
        HolidayBagReminderPage(
            uiState = HolidayBagReminderUiState.Loading,
            onEditModeToggle = { },
            onItemCheckedChange = { string: String, bool: Boolean -> },
            onDurationChange = { },
            onReinitialize = { },
            initializeBagDialogUiState = null,
            onValidateReinitialize = { },
            onCancelReinitialize = { },
            onReinitializeDurationChange = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayBagReminderScreenErrorPreview() {
    MaterialTheme {
        HolidayBagReminderPage(
            uiState = HolidayBagReminderUiState.Error("Failed to load holiday data"),
            onEditModeToggle = { },
            onItemCheckedChange = { string: String, bool: Boolean -> },
            onDurationChange = { },
            onReinitialize = { },
            initializeBagDialogUiState = null,
            onValidateReinitialize = { },
            onCancelReinitialize = { },
            onReinitializeDurationChange = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayBagReminderScreenEditingPreview() {
    MaterialTheme {
        HolidayBagReminderPage(
            uiState = HolidayBagReminderUiState.Value(
                name = "Summer Vacation",
                durationDay = 14,
                items = listOf(
                    ItemInBagUiState(
                        id = "swimsuit",
                        name = "Swimsuit",
                        checked = true,
                        quantity = 2
                    ),
                    ItemInBagUiState(
                        id = "sunglasses",
                        name = "Sunglasses",
                        checked = false,
                        quantity = 1
                    )
                ),
                isEditing = true
            ),
            onEditModeToggle = { },
            onItemCheckedChange = { string: String, bool: Boolean -> },
            onDurationChange = { },
            onReinitialize = { },
            initializeBagDialogUiState = null,
            onValidateReinitialize = { },
            onCancelReinitialize = { },
            onReinitializeDurationChange = { },
        )
    }
}