package m.adrien.kmpholiday.data.converter

import m.adrien.kmpholiday.data.HolidayItemData
import m.adrien.kmpholiday.domain.ItemInBag

fun HolidayItemData.toDomain(): ItemInBag {
    return ItemInBag(
        name = this.name,
        id = this.id,
        quantity = this.quantity,
        checked = false, //TODO
        isDurationDependant = this.isDayDependant
    )
}

fun ItemInBag.toData(): HolidayItemData {
    return HolidayItemData(
        name = this.name,
        id = this.id,
        quantity = this.quantity,
        isDayDependant = this.isDurationDependant
    )
}