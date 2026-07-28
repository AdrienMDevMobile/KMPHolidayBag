package m.adrien.kmpholiday.view.holidayBag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import m.adrien.kmpholiday.view.holidayBag.component.HolidayHeader
import m.adrien.kmpholiday.view.holidayBag.component.ItemsInBagList
import m.adrien.kmpholiday.view.holidayBag.value.HolidayBagNavigationEvent
import m.adrien.kmpholiday.view.holidayBag.value.HolidayBagReminderUiState
import m.adrien.kmpholiday.view.holidayBag.value.InitializeBagDialogUiState
import m.adrien.kmpholiday.view.holidayBag.value.ItemInBagUiState
import m.adrien.kmpholiday.view.shared.ErrorPage
import m.adrien.kmpholiday.view.shared.LoadingPage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HolidayBagReminderScreen(
    viewModel: HolidayBagReminderViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Get current duration for the dialog
    val currentDuration = when (val state = uiState) {
        is HolidayBagReminderUiState.Value -> state.durationDay
        else -> 0
    }

    // Get the initialize dialog state from view model
    val initializeDialogUiState by viewModel.initializeBagDialogUiState.collectAsState(null)

    val events by viewModel.navigationEvents.collectAsState()

    // Handle navigation events as a cold flow
    LaunchedEffect(events) {
        events.firstOrNull()?.let {
            when (it) {
                HolidayBagNavigationEvent.NavigateBack -> {
                    onNavigateBack()
                    viewModel.onNavigationEventProcessed(it.id)
                }
            }
        }
    }

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
        onBackPressed = { viewModel.onBackPressed() },
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
    onBackPressed: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
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
                        .padding(16.dp)
                        .windowInsetsPadding(WindowInsets.safeDrawing),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    HolidayHeader(
                        name = uiState.name,
                        durationDay = uiState.durationDay,
                        isEditing = uiState.isEditingOn,
                        onEditModeToggle = onEditModeToggle,
                        onDurationChange = onDurationChange,
                        onReinitialize = onReinitialize,
                        onBackPressed = onBackPressed,
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
        // Simplified preview - in a real app you would use a proper mock/view model
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
                isEditingOn = false
            ),
            onEditModeToggle = { },
            onItemCheckedChange = { string: String, bool: Boolean -> },
            onDurationChange = { },
            onReinitialize = { },
            initializeBagDialogUiState = null,
            onValidateReinitialize = { },
            onCancelReinitialize = { },
            onReinitializeDurationChange = { },
            onBackPressed = { },
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
                isEditingOn = false
            ),
            onEditModeToggle = { },
            onItemCheckedChange = { string: String, bool: Boolean -> },
            onDurationChange = { },
            onReinitialize = { },
            initializeBagDialogUiState = null,
            onValidateReinitialize = { },
            onCancelReinitialize = { },
            onReinitializeDurationChange = { },
            onBackPressed = { },
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
            onBackPressed = { },
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
            onBackPressed = { },
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
                isEditingOn = true
            ),
            onEditModeToggle = { },
            onItemCheckedChange = { string: String, bool: Boolean -> },
            onDurationChange = { },
            onReinitialize = { },
            initializeBagDialogUiState = null,
            onValidateReinitialize = { },
            onCancelReinitialize = { },
            onReinitializeDurationChange = { },
            onBackPressed = { },
        )
    }
}