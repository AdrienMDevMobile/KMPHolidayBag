package m.adrien.kmpholiday.data.converter

import m.adrien.kmpholiday.data.ItemInBagData
import m.adrien.kmpholiday.domain.ItemInBag

fun ItemInBagData.toDomain(): ItemInBag {
    return ItemInBag(
        name = this.name,
        id = this.id,
        quantity = this.quantity,
        checked = false, //TODO
        isDurationDependant = this.isDayDependant
    )
}

fun ItemInBag.toData(): ItemInBagData {
    return ItemInBagData(
        name = this.name,
        id = this.id,
        quantity = this.quantity,
        isDayDependant = this.isDurationDependant
    )
}