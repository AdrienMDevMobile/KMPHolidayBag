package m.adrien.kmpholiday.data

internal object StaticDatas {
    val listOfHolidayReminder = listOf(
        HolidayReminderData(
            name = "Plage",
            id = "1",
            duration = 7,
            items = listOf(
                HolidayItemData(
                    name = "Crême solaire",
                    id = "11",
                    isDayDependant = false,
                    quantity = 1,
                ),
                HolidayItemData(
                    name = "T shirt",
                    id = "12",
                    isDayDependant = true,
                    quantity = 1,
                )
            )
        ),
        HolidayReminderData(
            name = "Week end Danse",
            id = "2",
            duration = 2,
            items = listOf(
                HolidayItemData(
                    name = "T shirt",
                    id = "21",
                    isDayDependant = true,
                    quantity = 2,
                )
            )
        ),
        HolidayReminderData(
            name = "Ski",
            id = "3",
            duration = 7,
            items = listOf(
                HolidayItemData(
                    name = "T shirt",
                    id = "31",
                    isDayDependant = true,
                    quantity = 2,
                ),
                HolidayItemData(
                    name = "Lunettes de soleil",
                    id = "32",
                    isDayDependant = true,
                    quantity = 2,
                )
            )
        ),
    )
}