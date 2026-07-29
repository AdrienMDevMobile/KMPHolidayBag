package m.adrien.kmpholiday.view.settings.value

import m.adrien.kmpholiday.view.shared.NavigationEvent

sealed class SettingsNavigationEvent: NavigationEvent() {
    data object NavigateBack : SettingsNavigationEvent()
}