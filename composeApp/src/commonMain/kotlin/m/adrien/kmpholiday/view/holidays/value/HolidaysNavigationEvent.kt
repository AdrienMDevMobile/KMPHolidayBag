package m.adrien.kmpholiday.view.holidays.value

import m.adrien.kmpholiday.view.shared.NavigationEvent

sealed class HolidaysNavigationEvent : NavigationEvent() {
    data class NavigateToHoliday(val holidayId: String) : HolidaysNavigationEvent()
}