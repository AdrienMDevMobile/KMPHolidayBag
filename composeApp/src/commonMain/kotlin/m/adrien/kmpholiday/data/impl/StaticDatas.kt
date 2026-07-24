package m.adrien.kmpholiday.data.impl

import m.adrien.kmpholiday.data.HolidayReminderFrameworkData
import m.adrien.kmpholiday.data.ItemInBagData

internal object StaticDatas {
    val listOfHolidayBagReminder = listOf(
        HolidayReminderFrameworkData(
            name = "Plage",
            id = "1",
            items = listOf(
                ItemInBagData(
                    name = "Crême solaire",
                    id = "11",
                    isDayDependant = false,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "T shirt",
                    id = "12",
                    isDayDependant = true,
                    quantity = 1,
                )
            )
        ),
        HolidayReminderFrameworkData(
            name = "Week end Danse",
            id = "2",
            items = listOf(
                ItemInBagData(
                    name = "T shirt",
                    id = "21",
                    isDayDependant = true,
                    quantity = 2,
                )
            )
        ),
        HolidayReminderFrameworkData(
            name = "Ski",
            id = "3",
            items = listOf(
                ItemInBagData(
                    name = "T shirt",
                    id = "31",
                    isDayDependant = true,
                    quantity = 2,
                ),
                ItemInBagData(
                    name = "Lunettes de soleil",
                    id = "32",
                    isDayDependant = true,
                    quantity = 2,
                )
            )
        ),
    )
}