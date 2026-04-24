package m.adrien.kmpholiday.data.converter

import m.adrien.kmpholiday.data.HolidayItemData
import m.adrien.kmpholiday.domain.HolidayItem

fun HolidayItemData.toDomain(): HolidayItem {
    return HolidayItem(
        name = this.name,
        id = this.id,
        quantity = this.quantity,
        isDayDependant = this.isDayDependant
    )
}

fun HolidayItem.toData(): HolidayItemData {
    return HolidayItemData(
        name = this.name,
        id = this.id,
        quantity = this.quantity,
        isDayDependant = this.isDayDependant
    )
}